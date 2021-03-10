package com.icoffee.common.dto;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * @Name AuthorityDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-02-26 9:37
 */
@Data
@AllArgsConstructor
public class AuthorityDto {

    /**
     * 授权名称
     */
    private String name;
    /**
     * 资源URI
     */
    private String uri;
    /**
     * 资源URI的方法
     */
    private String method;
    /**
     * 授权标签
     */
    private String permission;

    /**
     * 归属模块
     */
    private String module;
}
