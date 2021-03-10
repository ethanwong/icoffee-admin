package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.RoleAuthority;
import com.icoffee.system.mapper.RoleAuthorityMapper;
import com.icoffee.system.service.RoleAuthorityService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name RoleAuthorityServiceImpl
 * @Description ${DESCRIPTION}
 * @Author lincy
 * @Create 2020-02-27 15:31
 */
@Service
@Log4j2
public class RoleAuthorityServiceImpl extends MpBaseServiceImpl<RoleAuthorityMapper, RoleAuthority> implements RoleAuthorityService {

    @Override
    public void saveRoleAuthority(String roleId, List<String> authIdResult) {
        //先移除旧数据
        this.getBaseMapper().delete(Wrappers.<RoleAuthority>lambdaQuery().eq(RoleAuthority::getRoleId, roleId));

        //保存新数据
        List<RoleAuthority> roleAuthoritys = new ArrayList<>();
        for (String authId : authIdResult) {
            roleAuthoritys.add(new RoleAuthority(roleId, authId));
        }
        saveBatch(roleAuthoritys);
    }

    @Override
    public List<String> getAuthIdsByRoleId(String roleId) {
        List<RoleAuthority> list = this.getBaseMapper().selectList(Wrappers.<RoleAuthority>lambdaQuery().eq(RoleAuthority::getRoleId, roleId));
        List<String> result = new ArrayList<>();
        for (RoleAuthority roleAuthority : list) {
            result.add(roleAuthority.getAuthorityId());
        }
        return result;
    }
}
