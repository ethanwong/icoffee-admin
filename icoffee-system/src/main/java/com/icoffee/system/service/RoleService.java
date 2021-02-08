package com.icoffee.system.service;


import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.Role;

/**
 * @Name RoleService
 * @Description 角色事务接口
 * @Author lincy
 * @Create 2020-02-21 15:32
 */
public interface RoleService extends MpBaseService<Role> {


    /**
     * 保存角色
     *
     * @param role 角色实体
     * @return
     */
    ResultDto saveEntity(Role role);

    /**
     * 修改角色
     *
     * @param role 角色实体
     * @return
     */
    ResultDto updateEntity(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    ResultDto deleteRoleById(String id);
}
