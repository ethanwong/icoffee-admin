package com.icoffee.security.config.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import com.icoffee.common.exception.TokenAuthCode;
import com.icoffee.common.exception.TokenAuthException;
import com.icoffee.security.dto.AuthErrorResponseBodyDto;
import com.icoffee.security.dto.TokenType;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.authentication.AuthenticationCredentialsNotFoundException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

/**
 * @Name JwtAuthenticationFilter
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-27 10:06
 */
@Component
@Log4j2
public class JwtAuthenticationFilter extends GenericFilterBean {

    /**
     * 请求header头参数名称
     */
    @Value("${jwt.token.headerName}")
    private String JWT_HEADER_NAME;
    /**
     * bearer token前缀
     */
    @Value("${jwt.token.bearerPrefix}")
    private String PREFIX_BEARER;

    /**
     * token秘钥
     */
    @Value("${jwt.token.secretKey}")
    private String JWT_SECRET_KEY;

    @Autowired
    private JwtProvider jwtTokenProvider;
    private final AuthenticationManagerBuilder authenticationManagerBuilder;
    @Autowired
    private UserDetailsService userDetailsService;

    public JwtAuthenticationFilter(AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException, AccessDeniedException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;
        String token = resolveToken(httpServletRequest);

        //token类型，默认为ACCESS TOKEN
        TokenType tokenType = TokenType.ACCESS;

        if (StringUtils.isNotBlank(token)) {
            Payload payload = null;
            try {
                payload = jwtTokenProvider.decodeTokenPayload(token);
                tokenType = TokenType.valueOf(payload.getClaim("type").asString());
            } catch (Exception e) {
                log.error(e.getMessage());
            }

            //校验token是否合法
            DecodedJWT decodedJWT = null;
            try {
                decodedJWT = jwtTokenProvider.verifyToken(token, JWT_SECRET_KEY);
            } catch (TokenAuthException e) {
                log.error(e.getMessage());
                //捕获token校验失败异常
                httpServletResponse.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
                httpServletResponse.setContentType("application/json;charset:utf-8");

                //token校验异常类型
                TokenAuthCode code = e.getCode();
                AuthErrorResponseBodyDto errorResponseBodyDto = new AuthErrorResponseBodyDto(HttpServletResponse.SC_UNAUTHORIZED, code.toString(), "Unauthorized," + e.getMessage(), tokenType);
                httpServletResponse.getWriter().write(errorResponseBodyDto.toJson());
                return;
            }

            if (decodedJWT != null) {
                String useranme = decodedJWT.getClaim("username").asString();
                UserDetails userDetails = userDetailsService.loadUserByUsername(useranme);
                Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
            }

        }
        try {
            chain.doFilter(request, response);
        } catch (AccessDeniedException e) {
            log.error(e.getMessage());
            //捕获无访问权限异常
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.setContentType("application/json;charset:utf-8");

            AuthErrorResponseBodyDto errorResponseBodyDto = new AuthErrorResponseBodyDto(HttpServletResponse.SC_FORBIDDEN, "Forbidden", "Forbidden," + e.getMessage(), tokenType);
            httpServletResponse.getWriter().write(errorResponseBodyDto.toJson());
            return;
        } catch (AuthenticationCredentialsNotFoundException e) {
            log.error(e.getMessage());
            //捕获Header头没有Authentication参数
            httpServletResponse.setStatus(HttpServletResponse.SC_FORBIDDEN);
            httpServletResponse.setContentType("application/json;charset:utf-8");
            AuthErrorResponseBodyDto errorResponseBodyDto = new AuthErrorResponseBodyDto(HttpServletResponse.SC_FORBIDDEN, "Forbidden", "Unauthorized," + e.getMessage(), tokenType);
            httpServletResponse.getWriter().write(errorResponseBodyDto.toJson());
            return;
        }

    }

    /**
     * 获取bearertoken
     *
     * @param request
     * @return
     */
    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(JWT_HEADER_NAME);
        if (bearerToken != null && StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(PREFIX_BEARER)) {
            // 去掉令牌前缀
            return bearerToken.replace(PREFIX_BEARER, "").trim();
        } else {
            log.debug("非法token:{},不是以Bearer字符串开头", bearerToken);
        }
        return null;
    }


}
