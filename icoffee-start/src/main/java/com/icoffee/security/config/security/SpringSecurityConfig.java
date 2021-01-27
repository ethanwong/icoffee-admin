package com.icoffee.security.config.security;

import com.icoffee.security.config.security.authentication.JwtAuthenticationFilter;
import com.icoffee.security.config.security.authorization.JwtAuthenticationEntryPoint;
import com.icoffee.security.config.security.authorization.JwtAccessDeniedHandler;
import com.icoffee.security.config.security.authorization.SecurityAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @Name SpringSecurityConfig
 * @Description Spring Security配置
 * @Author huangyingfeng
 * @Create 2020-02-27 11:42
 */
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true, securedEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityAuthorizationFilter securityAuthorizationFilter;
    private final JwtAccessDeniedHandler jwtAccessDeniedHandler;
    private final JwtAuthenticationEntryPoint jwtAuthenticationEntryPoint;

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception {
        return super.authenticationManagerBean();
    }

    @Bean
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        CorsConfiguration config = new CorsConfiguration();
        config.setAllowCredentials(true);
        config.addAllowedOriginPattern("*");
        config.addAllowedHeader("*");
        config.addAllowedMethod("*");
        source.registerCorsConfiguration("/**", config);
        return new CorsFilter(source);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {

        http.csrf().disable()
                //此处是前后端分离最大的区别，取消session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authorizeRequests()
                .antMatchers("/security/login", "/security/captcha").permitAll()
                .anyRequest().authenticated()


                .and()
                .headers().frameOptions().disable()
                .and()
                .rememberMe().disable()
                //添加JWT TOKEN Filter,注意Filter的顺序
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //添加资源鉴权 Filter,注意Filter的顺序
                .addFilterAfter(securityAuthorizationFilter, JwtAuthenticationFilter.class)
                //添加CORS TOKEN Filter
                .addFilterBefore(corsFilter(), SecurityAuthorizationFilter.class)
                //授权异常处理
                .exceptionHandling()
                .accessDeniedHandler(jwtAccessDeniedHandler)
                .authenticationEntryPoint(jwtAuthenticationEntryPoint)
        ;
    }


}
