package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.*;
import com.icoffee.system.dto.XTreeDto;
import com.icoffee.system.mapper.RoleMapper;
import com.icoffee.system.service.*;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Name RoleServiceImpl
 * @Description ${DESCRIPTION}
 * @Author lincy
 * @Create 2020-02-21 15:32
 */
@Service
@Log4j2
public class RoleServiceImpl extends MpBaseServiceImpl<RoleMapper, Role> implements RoleService {


    @Autowired
    private AuthorityService authorityService;
    @Autowired
    private RoleAuthorityService roleAuthorityService;
    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private MenuService menuService;

    @Override
    public boolean existsRoleName(String roleName) {
        Role role = getBaseMapper().selectOne(
                Wrappers.<Role>lambdaQuery().eq(Role::getName, roleName));
        if (role == null) {
            return false;
        }
        return true;
    }

    @Override
    public ResultDto saveEntity(Role role) {
        try {
            if (existsRoleName(role.getName())) {
                return ResultDto.returnFail("角色已存在");
            }
            save(role);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("保存角色出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public ResultDto updateEntity(Role role) {
        try {
            if (existsRoleName(role.getName())) {
                Role entity = getBaseMapper().selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, role.getName()));
                if (!role.getId().equals(entity.getId())) {
                    return ResultDto.returnFail("角色已存在");
                }
            }
            updateById(role);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("修改角色出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public ResultDto setRoleAuth(String roleId, List<String> menuIds, List<String> authIds) {
        try {
            if (StringUtils.isBlank(roleId)) {
                return ResultDto.returnFail("角色ID不能为空！");
            }
            QueryWrapper<RoleMenu> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("role_id", roleId);
            roleMenuService.deleteByWrapper(queryWrapper);

            QueryWrapper<RoleAuthority> queryWrapper2 = new QueryWrapper<>();
            queryWrapper2.eq("role_id", roleId);
            roleAuthorityService.deleteByWrapper(queryWrapper2);

            if (menuIds.size() > 0) {
                for (String menuId : menuIds) {
                    RoleMenu roleMenu = new RoleMenu();
                    roleMenu.setMenuId(menuId);
                    roleMenu.setRoleId(roleId);
                    roleMenuService.save(roleMenu);
                }
            }

            if (authIds.size() > 0) {
                for (String authId : authIds) {
                    RoleAuthority roleAuthority = new RoleAuthority();
                    roleAuthority.setAuthorityId(authId);
                    roleAuthority.setRoleId(roleId);
                    roleAuthorityService.save(roleAuthority);
                }
            }
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("设置角色权限出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public List<XTreeDto> getRoleAuth(String roleId) {
        List<XTreeDto> dtoList = new ArrayList<>();
        List<Menu> menuList = menuService.getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, "0").orderByAsc(Menu::getOrderNo));
        List<RoleAuthority> roleAuthorityList = roleAuthorityService.getBaseMapper().selectList(Wrappers.<RoleAuthority>lambdaQuery().eq(RoleAuthority::getRoleId, roleId));
        List<RoleMenu> roleMenuList = roleMenuService.getBaseMapper().selectList(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, roleId));
        for (Menu menu : menuList) {
            XTreeDto dto = new XTreeDto();
            dto.setName(menu.getTitle());
            dto.setValue(menu.getId());
            dto.setType("menu");
            for (RoleMenu roleMenu : roleMenuList) {
                if (menu.getId().equals(roleMenu.getMenuId())) {
                    dto.setChecked(true);
                    roleMenuList.remove(roleMenu);
                    break;
                }
            }
            getRecursionMenu(dto, roleMenuList, roleAuthorityList);
            dtoList.add(dto);
        }
        return dtoList;
    }

    private void getRecursionMenu(XTreeDto roleAuthDto, List<RoleMenu> roleMenuList, List<RoleAuthority> roleAuthorityList) {
        List<Menu> menuList = menuService.getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, roleAuthDto.getValue()).orderByAsc(Menu::getOrderNo));
        if (menuList.size() > 0) {
            for (Menu menu : menuList) {
                XTreeDto dto = new XTreeDto();
                dto.setName(menu.getTitle());
                dto.setType("menu");
                dto.setValue(menu.getId());
                for (RoleMenu roleMenu : roleMenuList) {
                    if (menu.getId().equals(roleMenu.getMenuId())) {
                        dto.setChecked(true);
                        roleMenuList.remove(roleMenu);
                        break;
                    }
                }
                getRecursionMenu(dto, roleMenuList, roleAuthorityList);
                roleAuthDto.getChildren().add(dto);
            }
        } else {
            List<Authority> authorityList = authorityService.getBaseMapper().selectList(Wrappers.<Authority>lambdaQuery().eq(Authority::getMenuId, roleAuthDto.getValue()));
            for (Authority authority : authorityList) {
                XTreeDto authDto = new XTreeDto();
                authDto.setName(authority.getName());
                authDto.setType("auth");
                authDto.setValue(authority.getId());
                for (RoleAuthority roleAuthority : roleAuthorityList) {
                    if (authority.getId().equals(roleAuthority.getAuthorityId())) {
                        authDto.setChecked(true);
                        roleAuthorityList.remove(roleAuthority);
                        break;
                    }
                }
                roleAuthDto.getChildren().add(authDto);
            }
        }
    }

    @Override
    public List<XTreeDto> findAuthorityByRoleId(String roleId) {
        return null;
    }
}
