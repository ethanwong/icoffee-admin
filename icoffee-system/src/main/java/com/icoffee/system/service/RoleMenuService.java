package com.icoffee.system.service;


import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.RoleMenu;

import java.util.List;

/**
 * @Name RoleMenuService
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2020-02-25 9:05
 */
public interface RoleMenuService extends MpBaseService<RoleMenu> {

    /**
     * 保存角色菜单信息
     * @param roleId
     * @param menuIdResult
     */
    void saveRoleMenu(String roleId, List<String> menuIdResult);

    /**
     * 根据角色ID获取菜单ID列表
     * @param roleId
     * @return
     */
    List<String> getMenuIdsByRoleId(String roleId);
}
