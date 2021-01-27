package com.icoffee.security.config.security.authentication;

import com.auth0.jwt.interfaces.DecodedJWT;
import com.auth0.jwt.interfaces.Payload;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.GenericFilterBean;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * @Name JwtTokenAuthenticationFilter
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-27 10:06
 */
@Component
@Log4j2
public class JwtAuthenticationFilter extends GenericFilterBean {

    private final String BEARER_HEADER = "Authorization";
    private final String PREFIX_BEARER = "Bearer";
    @Autowired
    private JwtProvider jwtTokenProvider;

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    org.springframework.security.core.userdetails.UserDetailsService userDetailsService;

    /**
     * token秘钥
     */
    @Value("${jwt.token.secretKey}")
    private String secretKey;

    public JwtAuthenticationFilter(AuthenticationManagerBuilder authenticationManagerBuilder) {
        this.authenticationManagerBuilder = authenticationManagerBuilder;
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        String token = resolveToken(httpServletRequest);

        if (StringUtils.isNotBlank(token)) {
            //TODO添加token刷新处理

            //校验token是否合法
            DecodedJWT decodedJWT = jwtTokenProvider.verifyToken(token, secretKey);

            Payload payload = jwtTokenProvider.decodeTokenPayload(token);

            String useranme = payload.getClaim("username").asString();
            UserDetails userDetails = userDetailsService.loadUserByUsername(useranme);

            Authentication authenticationToken = new UsernamePasswordAuthenticationToken(userDetails, token, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authenticationToken);

        }

        chain.doFilter(request, response);
    }


    private String resolveToken(HttpServletRequest request) {
        String bearerToken = request.getHeader(BEARER_HEADER);
        if (bearerToken != null && StringUtils.isNotBlank(bearerToken) && bearerToken.startsWith(PREFIX_BEARER)) {
            // 去掉令牌前缀
            return bearerToken.replace(PREFIX_BEARER, "").trim();
        } else {
            log.debug("非法token:{},不是以Bearer字符串开头", bearerToken);
        }
        return null;
    }


}
