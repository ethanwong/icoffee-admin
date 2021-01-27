package com.icoffee.security.web;

import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.exception.BadRequestException;
import com.icoffee.security.config.security.authentication.JwtProvider;
import com.icoffee.system.dto.LoginUserDto;
import com.icoffee.system.dto.PermissionDto;
import com.wf.captcha.utils.CaptchaUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @Name LoginController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("security")
public class LoginController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtProvider jwtTokenProvider;

    /**
     * 登录
     * @param request
     * @param loginUserDto
     * @param bindingResult
     * @return
     */
    @PostMapping("login")
    public ResultDto login(HttpServletRequest request, @Validated @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {

        if (!CaptchaUtil.ver(loginUserDto.getCaptcha(), request)) {
            throw new BadRequestException("验证码错误");
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            //生成TOKEN
            List<PermissionDto> permissionList = new ArrayList<>();
            String token = jwtTokenProvider.createToken(loginUserDto.getUsername(), permissionList);
            Map<String, String> tokenMap = new HashMap<>();
            tokenMap.put("token", token);
            return ResultDto.returnSuccessData(tokenMap);
        } catch (Exception e) {
            log.error("login error", e);
            throw new BadRequestException("账号或者密码错误");
        }

    }

}
