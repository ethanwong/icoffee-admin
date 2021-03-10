package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.RoleMenu;
import com.icoffee.system.mapper.RoleMenuMapper;
import com.icoffee.system.service.RoleMenuService;
import lombok.extern.log4j.Log4j2;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name RoleMenuServiceImpl
 * @Description ${DESCRIPTION}
 * @Author lincy
 * @Create 2020-02-25 9:05
 */
@Service
@Log4j2
public class RoleMenuServiceImpl extends MpBaseServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {

    @Override
    public void saveRoleMenu(String roleId, List<String> menuIdResult) {
        //先移除旧数据
        this.getBaseMapper().delete(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleId));

        //保存新数据
        List<RoleMenu> roleMenus = new ArrayList<>();
        for (String menuId : menuIdResult) {
            roleMenus.add(new RoleMenu(roleId, menuId));
        }
        saveBatch(roleMenus);
    }

    @Override
    public List<String> getMenuIdsByRoleId(String roleId) {
        List<RoleMenu> list = this.getBaseMapper().selectList(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleId));
        List<String> result = new ArrayList<>();
        for (RoleMenu roleMenu : list) {
            result.add(roleMenu.getMenuId());
        }
        return result;
    }
}
