package com.icoffee.common.utils;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.impl.JWTParser;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Header;
import com.auth0.jwt.interfaces.Payload;
import com.icoffee.common.exception.TokenAuthException;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.Assert;
import org.apache.tomcat.util.codec.binary.Base64;

/**
 * @Name JwtUtils
 * @Description JSON Web Tokens Utils
 * @Author huangyingfeng
 * @Create 2018-08-27 23:01
 */
@Log4j2
public class JwtTokenUtils {

    /**
     * 校验TOKEN
     *
     * @param token     TOKEN
     * @param secretKey SK
     * @return
     */
    public static DecodedJWT verifyToken(String token, String secretKey) {
        Assert.requireNonEmpty(token, "token不能为空！");
        Assert.requireNonEmpty(secretKey, "secretKey不能为空！");
        try {
            log.info("verifyToken token={}", token);
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (SignatureVerificationException exception) {
            log.error("verify error", exception);
            throw new TokenAuthException("token签名不匹配");
        } catch (TokenExpiredException exception) {
            log.error("verify error", exception);
            throw new TokenAuthException("token已经过期！");
        } catch (InvalidClaimException exception) {
            log.error("verify error", exception);
            throw new TokenAuthException("token无效！");
        } catch (JWTVerificationException exception) {
            log.error("verify error", exception);
            throw new TokenAuthException("token校验异常");
        }
    }

    /**
     * 解析TOKEN HEADER
     *
     * @param token TOKEN
     * @return
     */
    public static Header decodeTokenHeader(String token) {
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
    public static Payload decodeTokenPayload(String token) {
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
