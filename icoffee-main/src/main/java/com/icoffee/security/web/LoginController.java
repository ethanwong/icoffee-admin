package com.icoffee.security.web;

import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.exception.BadRequestException;
import com.icoffee.security.config.security.authentication.JwtProvider;
import com.icoffee.security.model.LoginResultDto;
import com.icoffee.security.service.UserService;
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

    @Autowired
    private UserService userService;

    /**
     * 登录
     *
     * @param request
     * @param loginUserDto
     * @param bindingResult
     * @return
     */
    @PostMapping("login")
    public ResultDto login(HttpServletRequest request, @Validated @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {

        if (!CaptchaUtil.ver(loginUserDto.getCaptcha(), request)) {
            return ResultDto.returnFail("验证码错误!");
        }

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            //生成TOKEN
            List<PermissionDto> permissionList = userService.getUserPermission(loginUserDto.getUsername());
            String token = jwtTokenProvider.createToken(loginUserDto.getUsername(), permissionList);

            LoginResultDto loginResultDto = new LoginResultDto();
            loginResultDto.setUsername(loginUserDto.getUsername());
            loginResultDto.setRole("");
            loginResultDto.setPermissionList(permissionList);
            loginResultDto.setToken(token);

            return ResultDto.returnSuccessMessageData("登录成功！", loginResultDto);
        } catch (Exception e) {
            log.error("login error", e);
            return ResultDto.returnFail("账号或者密码错误!");
        }

    }

}
