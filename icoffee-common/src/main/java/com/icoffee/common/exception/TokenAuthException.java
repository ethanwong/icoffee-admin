package com.icoffee.common.exception;

import org.springframework.security.core.AuthenticationException;

/**
 * @Name TokenAuthException
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2018-08-28 11:31
 */
public class TokenAuthException extends AuthenticationException {

    public TokenAuthException(String message) {
        super(message);
    }
}
