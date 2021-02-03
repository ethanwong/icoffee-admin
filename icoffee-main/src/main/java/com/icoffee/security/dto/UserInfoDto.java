package com.icoffee.security.dto;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name UserInfoDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-02-01 15:13
 */
@Data
public class UserInfoDto {
    private String username;
    private List<String> roles = new ArrayList<>();
    private String avatar;
    private List<RouteDto> routes;
}
