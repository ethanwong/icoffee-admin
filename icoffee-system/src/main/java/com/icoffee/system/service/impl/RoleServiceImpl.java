package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.Role;
import com.icoffee.system.domain.User;
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


}
