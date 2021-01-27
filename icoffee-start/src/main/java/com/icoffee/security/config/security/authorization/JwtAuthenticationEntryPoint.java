package com.icoffee.security.config.security.authorization;

import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.AuthenticationEntryPoint;
import org.springframework.stereotype.Component;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name JwtAuthenticationEntryPoint
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-27 16:56
 */
@Component
public class JwtAuthenticationEntryPoint implements AuthenticationEntryPoint {

    @Override
    public void commence(HttpServletRequest request, HttpServletResponse response, AuthenticationException authException) throws IOException, ServletException {
        // 当用户尝试访问安全的REST资源而不提供任何凭据时，将调用此方法发送401 响应
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.getOutputStream().write(authException == null ? "Unauthorized".getBytes() : authException.getMessage().getBytes());

    }
}
