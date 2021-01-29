package com.icoffee.security.web;

import cn.hutool.core.date.DateUtil;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.security.config.authentication.JwtProvider;
import com.icoffee.security.dto.LoginResultDto;
import com.icoffee.system.dto.LoginUserDto;
import com.icoffee.system.dto.PermissionDto;
import com.icoffee.system.service.UserService;
import com.wf.captcha.utils.CaptchaUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @Name SecurityController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
@Api(tags = {"认证API"})
@Log4j2
@RestController
@RequiredArgsConstructor
@RequestMapping("security")
public class SecurityController {

    private final AuthenticationManagerBuilder authenticationManagerBuilder;

    @Autowired
    private JwtProvider jwtTokenProvider;

    @Autowired
    private UserService userService;

    /**
     * 过期时间间隔
     */
    @Value("${jwt.token.expires}")
    private int EXPIRES;

    /**
     * 是否检查验证码
     */
    @Value("${security.login.captcha}")
    private boolean NEEDCAPTCHA;

    /**
     * 用户登录
     *
     * @param request
     * @param loginUserDto
     * @param bindingResult
     * @return
     */
    @ApiOperation(value = "登录", notes = "提交用户名、密码、验证码进行登录")
    @PostMapping("login")
    public ResultDto login(HttpServletRequest request, @Validated @RequestBody LoginUserDto loginUserDto, BindingResult bindingResult) {


        if (NEEDCAPTCHA && !CaptchaUtil.ver(loginUserDto.getCaptcha(), request)) {
            CaptchaUtil.clear(request);
            return ResultDto.returnFail("验证码错误!");
        }
        CaptchaUtil.clear(request);

        try {
            UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(loginUserDto.getUsername(), loginUserDto.getPassword());
            Authentication authentication = authenticationManagerBuilder.getObject().authenticate(authenticationToken);
            SecurityContextHolder.getContext().setAuthentication(authentication);


            //生成TOKEN
            List<PermissionDto> permissionList = userService.getUserPermission(loginUserDto.getUsername());
            String token = jwtTokenProvider.createToken(loginUserDto.getUsername(), permissionList);

            LoginResultDto loginResultDto = new LoginResultDto();
            loginResultDto.setUsername(loginUserDto.getUsername());
            loginResultDto.setExpireAt(DateUtil.offsetSecond(new Date(), EXPIRES / 1000));
            loginResultDto.setRole("");
            loginResultDto.setPermissionList(permissionList);
            loginResultDto.setToken(token);

            return ResultDto.returnSuccessMessageData("登录成功！", loginResultDto);
        } catch (Exception e) {
            log.error("login error", e);
            return ResultDto.returnFail("账号或者密码错误!");
        }

    }

    /**
     * 退出登录
     *
     * @param request
     * @return
     */
    @ApiOperation(value = "退出登录", notes = "")
    @PostMapping("logout")
    public ResultDto logout(HttpServletRequest request) {
        //todo 如果有设置缓存，需要将缓存中的token移除
        return ResultDto.returnSuccess();
    }

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
    @ApiOperation(value = "获取验证码", notes = "")
    @GetMapping("/captcha")
    public void captcha(HttpServletRequest request, HttpServletResponse response) throws Exception {
        CaptchaUtil.out(request, response);
    }

    /**
     * 校验验证码
     *
     * @param request
     * @param response
     * @param captcha
     * @return
     */
    @ApiOperation(value = "校验验证码", notes = "")
    @GetMapping("/captcha/check/{captcha}")
    public boolean check(HttpServletRequest request, HttpServletResponse response, @PathVariable String captcha) {
        String original = (String) request.getSession().getAttribute("captcha");
        log.info("input captcha={} , original captcha={}", captcha, original);

        return CaptchaUtil.ver(captcha, request);
    }

}
