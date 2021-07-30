package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.dto.PageDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.common.utils.SecurityUtils;
import com.icoffee.system.domain.Menu;
import com.icoffee.system.domain.Role;
import com.icoffee.system.domain.RoleMenu;
import com.icoffee.system.dto.ElTreeDto;
import com.icoffee.system.mapper.MenuMapper;
import com.icoffee.system.service.MenuService;
import com.icoffee.system.service.RoleMenuService;
import com.icoffee.system.service.RoleService;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * @Name MenuServiceImpl
 * @Description ${DESCRIPTION}
 * @Author lincy
 * @Create 2020-02-21 16:16
 */
@Service
@Log4j2
public class MenuServiceImpl extends MpBaseServiceImpl<MenuMapper, Menu> implements MenuService {

    @Autowired
    private RoleMenuService roleMenuService;
    @Autowired
    private MenuService menuService;
    @Autowired
    private RoleService roleService;

    /**
     * 设置子菜单
     *
     * @param parentMenu
     */
    private void setChildrenList(Menu parentMenu) {
        List<Menu> childrenList = getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, parentMenu.getId()).orderByAsc(Menu::getOrderNo));

        Menu sampleParentMenu = parentMenu.clone();
        if (childrenList != null && !childrenList.isEmpty()) {
            for (Menu child : childrenList) {
                child.setParent(sampleParentMenu);
                setChildrenList(child);
            }
            parentMenu.setChildren(childrenList);
        }
    }


    @Override
    public ResultDto getMenu() {

        List<Menu> parentList = menuService.getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, "0").orderByAsc(Menu::getOrderNo));

        for (Menu parentMenu : parentList) {
            this.setChildrenList(parentMenu);
        }

        return ResultDto.returnSuccessData(parentList);
    }

    @Override
    public List<Menu> getRootMenuList(QueryWrapper queryWrapper) {
        //获取跟级菜单
        queryWrapper.eq("parent_id", "0");
        queryWrapper.orderByAsc("order_no");
        List<Menu> parentList = menuService.getBaseMapper().selectList(queryWrapper);
        for (Menu parentMenu : parentList) {
            //获取子级菜单
            this.setChildrenList(parentMenu);
        }
        return parentList;
    }

    public List<Menu> getMenuByParentId(QueryWrapper queryWrapper) {

        List<Menu> parentList = menuService.getBaseMapper().selectList(queryWrapper);
        for (Menu parentMenu : parentList) {
            //获取子级菜单
            this.setChildrenList(parentMenu);
        }
        return parentList;
    }

    @Override
    public ResultDto getTree(String parentId) {
        //读取当前用户所拥有的菜单权限
        String username = SecurityUtils.getCurrentUsername();
        Role role = roleService.getRoleByUsername(username);
        //获取当前用户所属角色所关联的菜单
        List<String> checkMenuIds = roleMenuService.getMenuIdsByRoleId(role.getId());

        List<ElTreeDto> elTreeDtoList = new ArrayList<>();
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();

        //获取跟级菜单
        queryWrapper.eq("parent_id", parentId);
        queryWrapper.orderByAsc("order_no");

        List<Menu> rootMenuList = getMenuByParentId(queryWrapper);
        for (Menu menu : rootMenuList) {
            if (!checkMenuIds.contains(menu.getId())) {
                continue;
            }
            ElTreeDto elTreeDto = new ElTreeDto();
            this.menuToElTree(menu, elTreeDto);
            elTreeDtoList.add(elTreeDto);
        }

        return ResultDto.returnSuccessData(elTreeDtoList);
    }

    public void menuToElTree(Menu menu, ElTreeDto elTreeDto) {
        List<ElTreeDto> elTreeDtoChildrenList = new ArrayList<>();

        List<Menu> children = menu.getChildren();
        if (children != null && !children.isEmpty()) {
            for (Menu child : children) {
                ElTreeDto subElTreeDto = new ElTreeDto();
                menuToElTree(child, subElTreeDto);
                elTreeDtoChildrenList.add(subElTreeDto);
            }
        }
        elTreeDto.setId(menu.getId());
        elTreeDto.setName(menu.getTitle());
        elTreeDto.setModule(menu.getModuleName());
        elTreeDto.setParentId(menu.getParentId());
        elTreeDto.setChildren(elTreeDtoChildrenList);
        elTreeDto.setTag("MENU");

    }


    private boolean existsName(String title, String sourceId) {
        Menu menu = getBaseMapper().selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getTitle, title));
        if (menu == null) {
            return false;
        } else {
            if (StringUtils.isNotBlank(sourceId)) {
                //修改
                if (!sourceId.equals(menu.getId())) {
                    return true;
                }
            } else {
                //添加
                return true;
            }
        }
        return false;
    }


    private boolean existsRoutePath(String routePath, String sourceId) {
        Menu menu = getBaseMapper().selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getRoutePath, routePath));

        if (menu == null) {
            return false;
        } else {
            if (StringUtils.isNotBlank(sourceId)) {
                //修改
                if (!sourceId.equals(menu.getId())) {
                    return true;
                }
            } else {
                //添加
                return true;
            }
        }
        return false;
    }

    private boolean existsModuleName(String moduleName, String sourceId) {
        Menu menu = getBaseMapper().selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getModuleName, moduleName));
        if (menu == null) {
            return false;
        } else {
            if (StringUtils.isNotBlank(sourceId)) {
                //修改
                if (!sourceId.equals(menu.getId())) {
                    return true;
                }
            } else {
                //添加
                return true;
            }
        }
        return false;
    }

    @Override
    public ResultDto saveMenu(Menu menu) {
        try {
            if (existsName(menu.getTitle(), null)) {
                return ResultDto.returnFail("名称重复：" + menu.getTitle());
            }
            if (existsModuleName(menu.getModuleName(), null)) {
                return ResultDto.returnFail("模块名称重复：" + menu.getModuleName());
            }

            if (existsRoutePath(menu.getRoutePath(), null)) {
                return ResultDto.returnFail("路由地址重复：" + menu.getRoutePath());
            }

            if (StringUtils.isBlank(menu.getId())) {
                menu.setId(UUID.randomUUID().toString().replace("-", ""));
            }
            save(menu);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("保存菜单出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }


    @Override
    public ResultDto updateMenu(Menu menu) {
        try {
            if (existsName(menu.getTitle(), menu.getId())) {
                return ResultDto.returnFail("名称重复：" + menu.getTitle());
            }

            if (existsModuleName(menu.getModuleName(), menu.getId())) {
                return ResultDto.returnFail("模块名称重复：" + menu.getModuleName());
            }

            if (existsRoutePath(menu.getRoutePath(), menu.getId())) {
                return ResultDto.returnFail("路由地址重复：" + menu.getRoutePath());
            }

            Menu oldMenu = getById(menu.getId());
            menu.setCreateAt(oldMenu.getCreateAt());
            menu.setUpdateAt(System.currentTimeMillis());
            updateById(menu);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("修改菜单出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public ResultDto delete(String id) {
        try {
            List<Menu> menuList = getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, id));
            if (menuList.size() > 0) {
                return ResultDto.returnFail("有多个子级，不可删除");
            }
            List<RoleMenu> roleMenuList = roleMenuService.getBaseMapper().selectList(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getMenuId, id));
            if (roleMenuList.size() > 0) {
                return ResultDto.returnFail("失败：已被角色关联");
            }
            removeById(id);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("删除菜单出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }


    @Override
    public PageDto<Menu> findPage(int pageNo, int pageSize) {
        QueryWrapper<Menu> queryWrapper = new QueryWrapper<>();
        queryWrapper.orderByDesc("create_at", "order_no");
        return selectPage(queryWrapper, pageNo, pageSize);
    }

    @Override
    public Menu getMenuByModuleName(String moduleName) {
        Menu menu = this.getBaseMapper().selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getModuleName, moduleName));
        String parentId = menu.getId();
        List<Menu> childrens = menuService.getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, parentId));
        menu.setChildren(childrens);
        return menu;
    }

    @Override
    public Menu getAllMenuInfoById(String menuId) {
        //菜单基本信息
        Menu menu = this.getById(menuId);
        //设置子级别菜单
        this.setChildrenList(menu);
        //设置父级菜单
        menu.setParent(this.getById(menu.getParentId()));

        return menu;
    }
}
