package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.*;
import com.icoffee.system.dto.RoleMenuAuthDto;
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
    @Autowired
    private UserService userService;

    @Override
    public ResultDto saveEntity(Role role) {
        try {

            Role checkRole = getBaseMapper().selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, role.getName()));

            if (checkRole != null) {
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

            Role checkRole = getBaseMapper().selectOne(Wrappers.<Role>lambdaQuery().eq(Role::getName, role.getName()));

            if (checkRole != null && !checkRole.getId().equals(role.getId())) {
                return ResultDto.returnFail("角色已存在");
            }
            role.setUpdateAt(System.currentTimeMillis());
            updateById(role);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("修改角色出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public ResultDto deleteRoleById(String id) {

        //判断角色是否关用户
        List<User> userList = userService.getUserListByRoleId(id);
        if (userList != null && !userList.isEmpty()) {

            List<String> nameList = new ArrayList<>();
            for (User user : userList) {
                nameList.add(user.getUsername());
            }
            ResultDto.returnFail("删除错误，角色被如下用户关联：" + nameList.toString());
        }

        this.removeById(id);

        return ResultDto.returnSuccess();
    }

    @Override
    public ResultDto setRoleAuth(RoleMenuAuthDto roleMenuAuthDto) {
        try {


            /**
             * 角色ID
             */
            String roleId = roleMenuAuthDto.getRoleId();
            List<String> menuIds = roleMenuAuthDto.getMenuIds();
            List<String> authIds = roleMenuAuthDto.getAuthIds();

            /**
             * 菜单
             */
            List<String> menuIdResult = new ArrayList<>();
            /**
             * 授权
             */
            List<String> authIdResult = new ArrayList<>();

            //根据菜单修正数据
            for (String menuId : menuIds) {
                //将当前菜单ID,并保存
                menuIdResult.add(menuId);

                Menu menu = menuService.getAllMenuInfoById(menuId);

                //查找父级菜单ID,并保存
                List<String> menuIdsTemp = new ArrayList<>();
                this.setParentId(menuIdsTemp, menu);

                //查找子级菜单ID,并保存
                this.setChildrenId(menuIdsTemp, menu);

                for (String menuIdTemp : menuIdsTemp) {
                    if (!menuIdResult.contains(menuIdTemp)) {
                        menuIdResult.add(menuIdTemp);
                    }
                }

                //修正授权选中：全部选中menuId对应的菜单以及子菜单下的授权
                List<String> authIdTemp = new ArrayList<>();
                setChildAuthId(authIdTemp, menu);
                authIdResult.addAll(authIdTemp);
            }


            //根据授权修正数据
            for (String authId : authIds) {
                //添加已选中
                if (!authIdResult.contains(authId)) {
                    authIdResult.add(authId);
                }

                //修正菜单选中：全部选中authId对应授权关联的菜单以及父级菜单
                Authority authority = authorityService.getById(authId);
                String module = authority.getModule();
                Menu menu = menuService.getMenuByModuleName(module);

                List<String> menuIdsTemp = new ArrayList<>();
                if (!menuIdsTemp.contains(menu.getId())) {
                    menuIdsTemp.add(menu.getId());
                }
                setParentId(menuIdsTemp, menu);

                for (String menuId : menuIdsTemp) {
                    if (!menuIdResult.contains(menuId)) {
                        menuIdResult.add(menuId);
                    }
                }
            }


            log.info("menuIdResult = {}", menuIdResult);
            log.info("authIdResult = {}", authIdResult);

            //保存角色菜单信息
            roleMenuService.saveRoleMenu(roleId, menuIdResult);

            //保存角色授权信息
            roleAuthorityService.saveRoleAuthority(roleId, authIdResult);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error(e);
            return ResultDto.returnFail(e.getMessage());
        }

    }

    @Override
    public Role getRoleById(String id) {
        Role role = getById(id);

        List<RoleMenu> list = roleMenuService.getBaseMapper().selectList(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, id));

        for (RoleMenu roleMenu : list) {
            role.getMenuIds().add(roleMenu.getMenuId());
        }

        List<RoleAuthority> authList = roleAuthorityService.getBaseMapper().selectList(Wrappers.<RoleAuthority>lambdaQuery().eq(RoleAuthority::getRoleId, id));
        for (RoleAuthority roleAuthority : authList) {
            role.getAuthIds().add(roleAuthority.getAuthorityId());
        }

        return role;
    }

    private void setChildAuthId(List<String> authIdTemp, Menu menu) {
        List<Authority> authorities = authorityService.getByModule(menu.getModuleName());
        for (Authority authority : authorities) {
            if (!authIdTemp.contains(authority.getId())) {
                authIdTemp.add(authority.getId());
            }
        }
        if (menu.getChildren() != null && menu.getChildren().size() > 0) {
            for (Menu sub : menu.getChildren()) {
                setChildAuthId(authIdTemp, sub);
            }
        }
    }

    private void setChildrenId(List<String> result, Menu menu) {
        if (menu.getChildren() != null && menu.getChildren().size() > 0) {
            for (Menu child : menu.getChildren()) {
                if (!result.contains(menu.getId())) {
                    result.add(menu.getId());
                }
                setChildrenId(result, child);
            }
        }
    }


    private void setParentId(List<String> result, Menu menu) {
        if (menu.getParent() != null) {
            if (!result.contains(menu.getParent().getId())) {
                result.add(menu.getParent().getId());
            }
            setParentId(result, menu.getParent());
        }
    }


}
