package com.icoffee.system.service;


import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.RoleAuthority;

import java.util.List;

/**
 * @Name RoleAuthorityService
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2020-02-27 15:30
 */
public interface RoleAuthorityService extends MpBaseService<RoleAuthority> {
    /**
     * 保存角色授权信息
     * @param roleId
     * @param authIdResult
     */
    void saveRoleAuthority(String roleId, List<String> authIdResult);

    /**
     * 根据角色ID获取授权ID列表
     * @param id
     * @return
     */
    List<String> getAuthIdsByRoleId(String id);
}
