package com.icoffee.system.web;

import com.icoffee.common.annotation.AnonymousPostMapping;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.exception.BadRequestException;
import com.icoffee.system.dto.LoginUserDto;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.java.Log;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;

/**
 * @Name LoginController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Log4j2
@RestController
@RequiredArgsConstructor
public class LoginController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    private BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(4);

//    @AnonymousPostMapping("login")
//    public ResultDto login(HttpServletRequest request, @Validated @RequestBody LoginUserDto loginUserDto) {
//
//        if (!CaptchaUtil.ver(loginUserDto.getCaptcha(), request)) {
//            throw new BadRequestException("验证码错误");
//        }
//
//        try {
//            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword());
//            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
//            SecurityContextHolder.getContext().setAuthentication(authentication);
//        } catch (Exception e) {
//            log.error("login error", e);
//            throw new BadRequestException("账号或者密码错误");
//        }
//
//        return ResultDto.success("登录成功！");
//    }

}
