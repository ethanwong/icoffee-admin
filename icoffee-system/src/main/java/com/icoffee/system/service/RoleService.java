package com.icoffee.system.service;


import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.Role;
import com.icoffee.system.dto.XTreeDto;

import java.util.List;

/**
 * @Name RoleService
 * @Description 角色事务接口
 * @Author lincy
 * @Create 2020-02-21 15:32
 */
public interface RoleService extends MpBaseService<Role> {

    /**
     * 根据角色名查询角色信息是否存在
     *
     * @param roleName
     * @return
     */
    boolean existsRoleName(String roleName);

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
     * 获取角色权限
     *
     * @param roleId
     * @return
     */
    List<XTreeDto> getRoleAuth(String roleId);

    /**
     * 设置角色权限
     *
     * @param roleId
     * @param menuIds
     * @param authIds
     * @return
     */
    ResultDto setRoleAuth(String roleId, List<String> menuIds, List<String> authIds);

    /**
     * 根据角色Id查询所有的权限
     *
     * @param roleId 角色Id
     * @return List
     */
    List<XTreeDto> findAuthorityByRoleId(String roleId);
}
