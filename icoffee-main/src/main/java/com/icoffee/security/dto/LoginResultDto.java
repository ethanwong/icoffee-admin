package com.icoffee.security.dto;

import lombok.Data;

import java.util.Date;
import java.util.List;

/**
 * @Name LoginResultDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-28 15:25
 */
@Data
public class LoginResultDto {
    private String username;
    private String role;
    private String token;
    private Date expireAt;
    private List<RouteDto> permissionList;
}
