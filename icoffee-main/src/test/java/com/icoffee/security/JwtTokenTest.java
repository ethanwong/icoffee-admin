package com.icoffee.security;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTCreator;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.*;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.icoffee.common.exception.TokenAuthException;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.core.util.Assert;

import java.util.Date;
import java.util.UUID;

/**
 * @Name JwtTokenTest
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-27 16:07
 */
public class JwtTokenTest {

    private static final Long expires = 15 * 60 * 1000l;//过期时间
    private static final String issuer = "JC100-CENTER";//签发者

    public static String createToken(String tenant, String accessKey, String secretKey) throws TokenAuthException {
        Assert.requireNonEmpty(tenant, "tenant不能为空！");
        Assert.requireNonEmpty(accessKey, "accessKey不能为空!");
        Assert.requireNonEmpty(secretKey, "secretKey不能为空！");
        try {
            Date expiresAt = new Date();
            expiresAt.setTime(System.currentTimeMillis() + expires);//设置过期时间为5分钟
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTCreator.Builder tokenBuilder = JWT.create()
                    .withIssuer(issuer)//"Issuer —— 用于说明该JWT是由谁签发的"
                    .withIssuedAt(new Date())//"Issued At —— 数字类型，说明该JWT何时被签发"
                    .withExpiresAt(expiresAt)//"Expiration Time —— 数字类型，说明该JWT过期的时间"
                    .withAudience(tenant)//"Audience —— 用于说明该JWT发送给的用户"
                    .withJWTId(UUID.randomUUID().toString())//"JWT ID —— 说明标明JWT的唯一ID"
                    ;


            String token = tokenBuilder.sign(algorithm);
            return token;
        } catch (JWTCreationException exception) {
            throw new TokenAuthException(exception.getMessage());
        }
    }

    public static DecodedJWT verifyToken(String token, String secretKey) throws TokenAuthException {
        Assert.requireNonEmpty(token, "token不能为空！");
        Assert.requireNonEmpty(secretKey, "secretKey不能为空！");
        try {
            Algorithm algorithm = Algorithm.HMAC256(secretKey);
            JWTVerifier verifier = JWT.require(algorithm).build();
            return verifier.verify(token);
        } catch (SignatureVerificationException exception) {
            throw new TokenAuthException("token签名不匹配");
        } catch (TokenExpiredException exception) {
            throw new TokenAuthException("token已经过期！");
        } catch (InvalidClaimException exception) {
            throw new TokenAuthException("token无效！");
        } catch (JWTVerificationException exception) {

            throw new TokenAuthException("token校验异常");
        }
    }

    public static void main(String[] args) throws TokenAuthException {
        String secretKey1 = "12312312312";
        String secretKey2 = "12312312312";
        String token = JwtTokenTest.createToken("1", "1", secretKey1);
        JwtTokenTest.verifyToken(token, secretKey2);
    }
}
