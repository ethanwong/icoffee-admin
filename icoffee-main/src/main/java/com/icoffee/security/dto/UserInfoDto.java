package com.icoffee.security.dto;

import com.icoffee.common.dto.AuthorityDto;
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
    /**
     * ID
     */
    private String id;
    /**
     * 登录账号
     */
    private String username;
    /**
     * 真实姓名
     */
    private String realname;
    /**
     * 头像
     */
    private String avatar;
    /**
     * 性别:1-男，2-女
     */
    private Integer gender;
    /**
     * 手机号码
     */
    private String phoneNumber;
    /**
     * 邮箱地址
     */
    private String email;
    /**
     * 角色信息
     */
    private List<String> roles = new ArrayList<>();
    /**
     * 路由信息
     */
    private List<RouteDto> routes;
    /**
     * 授权信息
     */
    private List<AuthorityDto> permissions;
}
