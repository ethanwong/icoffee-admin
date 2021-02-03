package com.icoffee.system.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;


/**
 * @Name LoginUserDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:33
 */
@Data
public class LoginUserDto {

    @ApiModelProperty(value = "用户名")
    @NotBlank(message = "用户名不能为空！")
    private String username;

    @ApiModelProperty(value = "密码")
    @NotBlank(message = "密码不能为空！")
    private String password;
    @ApiModelProperty(value = "验证码")
    @NotBlank(message = "验证码不能为空！")
    private String code;
}
