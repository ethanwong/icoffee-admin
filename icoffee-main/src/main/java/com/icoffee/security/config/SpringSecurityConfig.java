package com.icoffee.security.config;

import com.icoffee.security.config.authentication.JwtAuthenticationFilter;
import com.icoffee.security.config.authorization.JwtAccessDeniedHandler;
import com.icoffee.security.config.authorization.JwtAuthenticationEntryPoint;
import com.icoffee.security.config.authorization.SecurityAuthorizationFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
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
@Log4j2
@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SpringSecurityConfig extends WebSecurityConfigurerAdapter {

    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final SecurityAuthorizationFilter securityAuthorizationFilter;

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
    public void configure(WebSecurity web) throws Exception {
        web.debug(false);
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable()
                //此处是前后端分离最大的区别，取消session
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().headers().frameOptions().disable()
                .and().rememberMe().disable()
                //添加JWT TOKEN Filter,注意Filter的顺序
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                //添加资源鉴权 Filter,注意Filter的顺序
                .addFilterAfter(securityAuthorizationFilter, UsernamePasswordAuthenticationFilter.class)
                //添加CORS TOKEN Filter
                .addFilterBefore(corsFilter(), SecurityAuthorizationFilter.class)
                //授权异常处理
                .exceptionHandling()
                .accessDeniedHandler(new JwtAccessDeniedHandler())
                .authenticationEntryPoint(new JwtAuthenticationEntryPoint())
                .and()
                .authorizeRequests()
                .antMatchers("/security/login", "/security/captcha").permitAll()
                .antMatchers("/swagger-ui/**","/v3/api-docs","/swagger-resources/**").permitAll()
                .anyRequest().authenticated()
        ;
    }
}
