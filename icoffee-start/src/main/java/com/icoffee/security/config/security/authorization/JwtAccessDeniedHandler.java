package com.icoffee.security.config.security.authorization;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.web.access.AccessDeniedHandler;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name JwtAccessDeniedHandler
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-27 16:54
 */
@Component
public class JwtAccessDeniedHandler implements AccessDeniedHandler {
    @Override
    public void handle(HttpServletRequest request, HttpServletResponse response, AccessDeniedException accessDeniedException) throws IOException, ServletException {
        //当用户在没有授权的情况下访问受保护的REST资源时，将调用此方法发送403 Forbidden响应
        response.setStatus(HttpServletResponse.SC_FORBIDDEN);
        response.getOutputStream().write(accessDeniedException == null ? "Forbidden".getBytes() : accessDeniedException.getMessage().getBytes());

    }
}