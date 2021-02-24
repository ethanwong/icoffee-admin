package com.icoffee.system.dto;

import lombok.Data;

import java.util.List;

/**
 * @Name RoleMenuAuthDto
 * @Description
 * @Author huangyingfeng
 * @Create 2021-02-24 16:13
 */
@Data
public class RoleMenuAuthDto {
    private String roleId;
    private List<String> menuIds;
    private List<String> authIds;
}
