package com.icoffee.security.dto;

import lombok.Data;

import java.util.UUID;

/**
 * @Name LoginResultDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-28 15:25
 */
@Data
public class LoginResultDto {
    private String uuid = UUID.randomUUID().toString().replace("-", "");
    private String username;
    private TokenDto accessToken;
    private TokenDto refreshToken;
}
