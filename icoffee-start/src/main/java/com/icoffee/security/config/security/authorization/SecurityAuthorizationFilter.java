package com.icoffee.security.config.security.authorization;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.ConfigAttribute;
import org.springframework.security.access.SecurityConfig;
import org.springframework.security.access.SecurityMetadataSource;
import org.springframework.security.access.intercept.AbstractSecurityInterceptor;
import org.springframework.security.access.intercept.InterceptorStatusToken;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.web.FilterInvocation;
import org.springframework.security.web.access.intercept.FilterInvocationSecurityMetadataSource;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.annotation.PostConstruct;
import javax.servlet.*;
import java.io.IOException;
import java.util.*;

/**
 * @Name SecurityAuthorizationFilter
 * @Description 资源授权Filter
 * @Author huangyingfeng
 * @Create 2021-01-27 11:50
 */
@Component
public class SecurityAuthorizationFilter extends AbstractSecurityInterceptor implements Filter {

    @Autowired
    private InvocationSecurityMetadataSource invocationSecurityMetadataSource;
    @Autowired
    private SecurityAccessDecisionManager securityAccessDecisionManager;
    @Autowired
    private AuthenticationManager authenticationManager;



    @PostConstruct
    public void init() {
        super.setAuthenticationManager(authenticationManager);
        super.setAccessDecisionManager(securityAccessDecisionManager);
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        FilterInvocation filterInvocation = new FilterInvocation(servletRequest, servletResponse, filterChain);
        invoke(filterInvocation);
    }

    public void invoke(FilterInvocation filterInvocation) throws IOException, ServletException {
        InterceptorStatusToken token = super.beforeInvocation(filterInvocation);
        try {
            filterInvocation.getChain().doFilter(filterInvocation.getRequest(), filterInvocation.getResponse());
        } finally {
            super.afterInvocation(token, null);
        }
    }

    @Override
    public Class<?> getSecureObjectClass() {
        return FilterInvocation.class;
    }

    @Override
    public SecurityMetadataSource obtainSecurityMetadataSource() {
        return invocationSecurityMetadataSource;
    }
}
