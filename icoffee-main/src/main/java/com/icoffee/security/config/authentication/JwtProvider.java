package com.icoffee.security.config.authentication;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Header;
import com.auth0.jwt.interfaces.Payload;
import com.icoffee.common.exception.TokenAuthException;
import com.icoffee.security.dto.RouteDto;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.Assert;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.List;
import java.util.UUID;

/**
 * @Name JwtTokenProvider
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-27 14:30
 */
@Component
@Log4j2
public class JwtProvider {

    /**
     * 过期时间间隔
     */
    @Value("${jwt.token.expires}")
    private Long EXPIRES;
    /**
     * 签发者
     */
    @Value("${jwt.token.issuer}")
    private String ISSUER;
    /**
     * 签发给谁
     */
    @Value("${jwt.token.audience}")
    private String AUDIENCE;
    /**
     * token秘钥
     */
    @Value("${jwt.token.secretKey}")
    private String SECRETKEY;

    /**
     * 创建token
     *
     * @param username
     * @param permissionDtoList
     * @return
     */
    public String createToken(String username, List<RouteDto> permissionDtoList) throws JWTCreationException {
        try {
            Date expiresAt = new Date();
            //设置过期时间
            expiresAt.setTime(System.currentTimeMillis() + EXPIRES);
            Algorithm algorithm = Algorithm.HMAC256(SECRETKEY);
            JWTCreator.Builder tokenBuilder = JWT.create()
                    //"Issuer —— 用于说明该JWT是由谁签发的"
                    .withIssuer(ISSUER)
                    //"Issued At —— 数字类型，说明该JWT何时被签发"
                    .withIssuedAt(new Date())
                    //"Expiration Time —— 数字类型，说明该JWT过期的时间"
                    .withExpiresAt(expiresAt)
                    //"Audience —— 用于说明该JWT发送给的用户"
                    .withAudience(AUDIENCE)
                    //"JWT ID —— 说明标明JWT的唯一ID"
                    .withJWTId(UUID.randomUUID().toString())
                    .withClaim("username", username)
                    .withClaim("permission", permissionDtoList);

            String token = tokenBuilder.sign(algorithm);
//            log.info("createToken token={}", token);
            return token;
        } catch (JWTCreationException exception) {
            log.error("createToken error", exception);
            throw new JWTCreationException(exception.getMessage(), exception);
        }
    }


    /**
     * 校验TOKEN
     *
     * @param token     TOKEN
     * @param secretKey SK
     * @return
     */
    public DecodedJWT verifyToken(String token, String secretKey) throws TokenAuthException {
        Assert.requireNonEmpty(token, "token不能为空！");
        Assert.requireNonEmpty(secretKey, "secretKey不能为空！");
        try {
//            log.info("verifyToken token={}", token);
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (SignatureVerificationException exception) {
            //"token签名不匹配"
            throw new TokenAuthException(exception.getMessage());
        } catch (TokenExpiredException exception) {
            //"token已经过期！"
            throw new TokenAuthException(exception.getMessage());
        } catch (InvalidClaimException exception) {
            //"token无效！"
            throw new TokenAuthException(exception.getMessage());
        } catch (JWTVerificationException exception) {
            //"token校验异常"
            throw new TokenAuthException(exception.getMessage());
        }
    }

    /**
     * 解析TOKEN HEADER
     *
     * @param token TOKEN
     * @return
     */
    public Header decodeTokenHeader(String token) throws TokenAuthException {
        Assert.requireNonEmpty(token);
        String[] parts = {};
        try {
            parts = StringUtils.split(token, ".");
        } catch (Exception e) {
            throw new TokenAuthException("token不正确！");
        }
        String headerJson;
        try {
            headerJson = new String(Base64.decodeBase64(parts[0]), "UTF-8");
        } catch (Exception var6) {
            log.error("decodeTokenHeader error", var6);
            throw new TokenAuthException("token不正确！");
        }
        JWTParser converter = new JWTParser();
        return converter.parseHeader(headerJson);
    }

    /**
     * 解析TOKEN PAYLOAD
     *
     * @param token TOKEN
     * @return
     */
    public Payload decodeTokenPayload(String token) throws TokenAuthException {
        Assert.requireNonEmpty(token, "token不能为空！");
        String[] parts = {};
        try {
            parts = StringUtils.split(token, ".");
        } catch (Exception e) {
            throw new TokenAuthException("token不正确！");
        }

        String payloadJson;
        try {
            payloadJson = new String(Base64.decodeBase64(parts[1]), "UTF-8");
        } catch (Exception var6) {
            log.error("decodeTokenPayload error", var6);
            throw new TokenAuthException("token不正确！");
        }
        JWTParser converter = new JWTParser();
        return converter.parsePayload(payloadJson);
    }
}
