package com.icoffee.common.exception;

import javax.security.auth.message.AuthException;

/**
 * @Name TokenAuthException
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-08-28 11:31
 */
public class TokenAuthException extends AuthException {

    private TokenAuthCode code;

    public TokenAuthException(TokenAuthCode code,String message) {
        super(message);
        this.code = code;
    }

    public TokenAuthException(String message) {
        super(message);
    }

    public TokenAuthCode getCode() {
        return code;
    }

    public void setCode(TokenAuthCode code) {
        this.code = code;
    }
}
