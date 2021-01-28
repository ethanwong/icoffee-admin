package com.icoffee.common.exception;

import javax.security.auth.message.AuthException;

/**
 * @Name TokenAuthException
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-08-28 11:31
 */
public class TokenAuthException extends AuthException {

    public TokenAuthException(String message) {
        super(message);
    }

}
