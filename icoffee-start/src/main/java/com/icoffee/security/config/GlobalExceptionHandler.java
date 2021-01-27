package com.icoffee.security.config;

import com.icoffee.common.exception.TokenAuthException;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Name GlobalExceptionHandler
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-27 17:18
 */
@ControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(AccessDeniedException.class)
    @ResponseBody
    public String handleAccessDeniedException(AccessDeniedException e){
        return e.getMessage();
    }

    @ExceptionHandler(TokenAuthException.class)
    @ResponseBody
    public String handleTokenAuthException(TokenAuthException e){
        return e.getMessage();
    }


}
