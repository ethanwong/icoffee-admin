package com.icoffee.system.dto;

import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;


/**
 * @Name LoginUserDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:33
 */
@Data
public class LoginUserDto {
    @NotEmpty(message = "用户名不能为空！")
    private String username;

    @NotEmpty(message = "密码不能为空！")
    private String password;
    @NotEmpty(message = "验证码不能为空！")
    private String captcha;
}
