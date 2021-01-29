package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.StringUtils;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.Authority;
import com.icoffee.system.domain.Menu;
import com.icoffee.system.domain.RoleMenu;
import com.icoffee.system.domain.User;
import com.icoffee.system.dto.MenuDto;
import com.icoffee.system.dto.XTreeDto;
import com.icoffee.system.mapper.MenuMapper;
import com.icoffee.system.service.AuthorityService;
import com.icoffee.system.service.MenuService;
import com.icoffee.system.service.RoleMenuService;
import com.icoffee.system.service.UserService;
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
    private UserService accountService;
    @Autowired
    private AuthorityService authorityService;


    @Override
    public ResultDto getMenu() {
        //TODO
        String username = "";
        User account = accountService.getByUsername(username);
        if (account == null) {
            return null;
        }

        List<RoleMenu> roleMenuList = roleMenuService.getBaseMapper().selectList(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getRoleId, account.getRoleId()));

        List<MenuDto> parentList = new ArrayList<>();
        MenuDto parent = null;
        List<Menu> parentApiList = getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, "0").orderByAsc(Menu::getOrderNo));
        for (Menu parentmenu : parentApiList) {
            for (RoleMenu roleMenu : roleMenuList) {//当前用户有权限菜单ID
                if (roleMenu.getMenuId().equals(parentmenu.getId())) {
//                    childList = Lists.newArrayList();
                    parent = new MenuDto();
                    parent.setTitle(parentmenu.getName());
                    parent.setName(parentmenu.getModule());
                    parent.setIcon(parentmenu.getIcon());
                    if (parentmenu.getIsJump() == 1) {
                        parent.setJump(parentmenu.getUri());
                    }
                    getRecursionMenu(parent, parentmenu, roleMenuList);
                    parentList.add(parent);
                    break;
                }
            }
        }
        return ResultDto.returnSuccessData(parentList);
    }

    private void getRecursionMenu(MenuDto parent, Menu parentmenu, List<RoleMenu> roleMenuList) {
        List<Menu> childApiList = getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, parentmenu.getId()).orderByAsc(Menu::getOrderNo));
        List<MenuDto> childList = new ArrayList<>();
        if (parentmenu.getIsJump() == 1) {
            parent.setJump(parentmenu.getUri());
        }
        for (Menu childmenu : childApiList) {
            for (RoleMenu childRoleMenu : roleMenuList) {
                if (childRoleMenu.getMenuId().equals(childmenu.getId())) {
                    MenuDto child = new MenuDto();
                    child.setTitle(childmenu.getName());
                    child.setName(childmenu.getModule());
                    if (childmenu.getIsJump() == 1) {
                        child.setJump(childmenu.getUri());
                    }
                    getRecursionMenu(child, childmenu, roleMenuList);
                    childList.add(child);
                    break;
                }
            }
            parent.setList(childList);
        }
    }

    @Override
    public List<XTreeDto> getTree() {
        List<XTreeDto> xTreeParentList = new ArrayList<>();
//        List<XTreeDto> xTreeChildList = null;
        XTreeDto xTreeParent = null;
//        XTreeDto xTreeChild = null;

        List<Menu> parentApiList = getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, "0").orderByAsc(Menu::getOrderNo));
        for (Menu parent : parentApiList) {
//            xTreeChildList = Lists.newArrayList();
            xTreeParent = new XTreeDto();
            xTreeParent.setName(parent.getName());
            xTreeParent.setValue(parent.getId());
            getRecursionTree(xTreeParent, parent.getId());
            xTreeParentList.add(xTreeParent);
//            List<Menu> childApiList = getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId,parent.getId()).orderByAsc(Menu::getOrderNo));
//            if(childApiList.size() == 0){
//                xTreeParent.setChildren(Lists.newArrayList());
//            }
//            for (Menu child : childApiList) {
//                xTreeChild = new XTreeDto();
//                xTreeChild.setName(child.getName());
//                xTreeChild.setValue(child.getId());
////                xTreeChild.setChildren(Lists.newArrayList());
//                xTreeChildList.add(xTreeChild);
//            }
//            xTreeParent.setChildren(xTreeChildList);
//            xTreeParentList.add(xTreeParent);
        }
        return xTreeParentList;
    }

    private void getRecursionTree(XTreeDto xTreeParent, String parentId) {
        List<Menu> childApiList = getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, parentId).orderByAsc(Menu::getOrderNo));
        if (childApiList.size() > 0) {
            List<XTreeDto> xTreeChildList = new ArrayList<>();
            for (Menu child : childApiList) {
                XTreeDto xTreeChild = new XTreeDto();
                xTreeChild.setName(child.getName());
                xTreeChild.setValue(child.getId());
//                xTreeChild.setChildren(Lists.newArrayList());
                xTreeChildList.add(xTreeChild);
                getRecursionTree(xTreeChild, child.getId());
                xTreeParent.setChildren(xTreeChildList);
            }
        } else {
            xTreeParent.setChildren(null);
        }
    }

    @Override
    public boolean existsName(String name) {
        Menu menu = getBaseMapper().selectOne(
                Wrappers.<Menu>lambdaQuery().eq(Menu::getName, name));
        if (menu == null) {
            return false;
        }
        return true;
    }

    @Override
    public boolean existsUri(String uri) {
        Menu menu = getBaseMapper().selectOne(
                Wrappers.<Menu>lambdaQuery().eq(Menu::getUri, uri));
        if (menu == null) {
            return false;
        }
        return true;
    }

    @Override
    public ResultDto saveEntity(Menu menu) {
        try {
            if (existsName(menu.getName())) {
                return ResultDto.returnFail("名称重复：" + menu.getName());
            }
            if (existsUri(menu.getUri())) {
                return ResultDto.returnFail("uri重复");
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
    public ResultDto updateEntity(Menu menu) {
        try {
            Menu checkMenu = getBaseMapper().selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getName, menu.getName()));
            if (checkMenu != null) {
                if (!menu.getId().equals(checkMenu.getId())) {
                    return ResultDto.returnFail("名称重复");
                }
            }
            checkMenu = getBaseMapper().selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getUri, menu.getUri()));
            if (checkMenu != null) {
                if (!menu.getId().equals(checkMenu.getId())) {
                    return ResultDto.returnFail("uri重复");
                }
            }
            Menu oldMenu = getById(menu.getId());
            if (!oldMenu.getParentId().equals("0")) {
                menu.setParentId(oldMenu.getParentId());
            }
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
//            Menu menu = getById(id);
            List<Menu> menuList = getBaseMapper().selectList(Wrappers.<Menu>lambdaQuery().eq(Menu::getParentId, id));
            if (menuList.size() > 0) {
                return ResultDto.returnFail("有多个子级，不可删除");
            }
            List<RoleMenu> roleMenuList = roleMenuService.getBaseMapper().selectList(Wrappers.<RoleMenu>lambdaQuery().eq(RoleMenu::getMenuId, id));
            if (roleMenuList.size() > 0) {
                return ResultDto.returnFail("失败：已被角色关联");
            }

            removeById(id);
//            menuAuthorityService.deleteAllByMenuId(id);
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            log.error("删除菜单出现异常,异常信息为:{}", e.getMessage());
            e.printStackTrace();
            return ResultDto.returnFail(e.getMessage());
        }
    }

    @Override
    public ResultDto getNameHierarchy(String uri) {
        List<String> list = new ArrayList<>();
        String[] modules = uri.split("/");
        String menuId = null;
        for (String module : modules) {
            Menu menu = getBaseMapper().selectOne(Wrappers.<Menu>lambdaQuery().eq(Menu::getModule, module));
            if (menu != null) {
                list.add(menu.getName());
                menuId = menu.getId();
                continue;
            }
            if (menuId != null) {
                Authority authority = authorityService.getBaseMapper().selectOne(Wrappers.<Authority>lambdaQuery().eq(Authority::getMenuId, menuId).eq(Authority::getModule, module));
                if (authority != null) {
                    list.add(authority.getName());
                }
            }
        }
        return ResultDto.returnSuccessData(list);
    }
}
