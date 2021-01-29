package com.icoffee.security.web;

import com.wf.captcha.utils.CaptchaUtil;
import lombok.extern.log4j.Log4j2;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @Name CaptchaController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-21 9:27
 */
@Log4j2
@RestController
@RequestMapping("security")
public class CaptchaController {

    /**
     * 获取验证码
     *
     * @param request
     * @param response
     * @throws Exception
     */
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
    @GetMapping("/captcha/check/{captcha}")
    public boolean check(HttpServletRequest request, HttpServletResponse response, @PathVariable String captcha) {
        String original = (String) request.getSession().getAttribute("captcha");
        log.info("input captcha={} , original captcha={}", captcha, original);

        return CaptchaUtil.ver(captcha, request);
    }
}
