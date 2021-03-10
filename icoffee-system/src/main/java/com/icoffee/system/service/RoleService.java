package com.icoffee.system.service;


import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.Role;
import com.icoffee.system.dto.RoleMenuAuthDto;

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
    ResultDto saveRole(Role role);

    /**
     * 修改角色
     *
     * @param role 角色实体
     * @return
     */
    ResultDto updateRole(Role role);

    /**
     * 删除角色
     * @param id
     * @return
     */
    ResultDto deleteRoleById(String id);

    /**
     * 设置角色菜单和授权
     * @param roleMenuAuthDto
     * @return
     */
    ResultDto setRoleAuth(RoleMenuAuthDto roleMenuAuthDto);

    /**
     * 获取角色信息
     * @param id
     * @return
     */
    Role getRoleById(String id);
}
