package com.icoffee.system.service.impl;

import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseServiceImpl;
import com.icoffee.system.domain.User;
import com.icoffee.system.dto.PermissionDto;
import com.icoffee.system.mapper.UserMapper;
import com.icoffee.system.service.UserService;
import lombok.extern.log4j.Log4j2;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Name UserServiceImpl
 * @Description 用户模块Service接口实现类
 * @Author chenly
 * @Create 2019-12-02 11:06
 */
@Service
@Log4j2
public class UserServiceImpl extends MpBaseServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

//    @Autowired
//    private UserRoleService userRoleService;

    @Override
    public User getByUsername(String username) {
        return getBaseMapper().selectOne(Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
    }

    @Override
    public ResultDto saveEntity(User userDO) {
        try {
            if (existsUsername(userDO.getUsername())) {
                return ResultDto.returnFail("账号已存在");
            }
            if (StringUtils.isBlank(userDO.getPassword())) {
                userDO.setPassword("123456");
            }
            userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
//            userDO.setAreaId(areaId.replace(",",""));
            save(userDO);
//            userRoleService.save(new UserRoleDO(userDO.getId(), roleId));
            return ResultDto.returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.returnFail("");
        }
    }

    @Override
    public ResultDto updateEntity(User userDO) {
        try {
            if (existsUsername(userDO.getUsername())) {
                User checkBean = getByUsername(userDO.getUsername());
                if (!userDO.getId().equals(checkBean.getId())) {
                    return ResultDto.returnFail("账号重复");
                }
            }

            User olduserDO = getById(userDO.getId());
            if (StringUtils.isBlank(userDO.getPassword())) {
                userDO.setPassword(olduserDO.getPassword());
            } else {
                userDO.setPassword(passwordEncoder.encode(userDO.getPassword()));
            }
//            userDO.setAreaId(olduserDO.getAreaId());
            userDO.setCreateAt(olduserDO.getCreateAt());
            userDO.setUpdateAt(System.currentTimeMillis());
            save(userDO);

            return ResultDto.returnSuccess();
        } catch (Exception e) {
            e.printStackTrace();
            return ResultDto.returnFail("");
        }
    }


    @Override
    public ResultDto update(String realname, String email, String phoneNumber) throws Exception {
        return null;
    }

    @Override
    public ResultDto resetPwd(String oldPwd, String newPwd) throws Exception {
        return null;
    }

    @Override
    public ResultDto changeLocked(String id) throws Exception {
        return null;
    }

    @Override
    public List<PermissionDto> getUserPermission(String username) {
        return null;
    }


    /**
     * 根据用户名查询用户信息是否存在
     *
     * @param username
     * @return
     */
    public boolean existsUsername(String username) {
        User userDO = getBaseMapper().selectOne(
                Wrappers.<User>lambdaQuery().eq(User::getUsername, username));
        if (userDO == null) {
            return false;
        }
        return true;
    }
//
//    /**
//     * 保存用户
//     *
//     * @param userDO 用户实体
//     * @param roleId 角色id
//     * @return
//     */
//    @Override
//    public ResultDTO saveUser(UserDO userDO, String roleId) {
//        try {
//
//            if (existsUsername(userDO.getUsername())) {
//                return ResultDTO.failed("账号已存在");
//            }
//            userDO.setPassword(new BCryptPasswordEncoder().encode(userDO.getPassword()));
//            save(userDO);
//            return ResultDTO.success();
//        } catch (Exception e) {
//            log.error("保存用户出现异常,异常信息为:{}", e.getMessage());
//            throw e;
//        }
//    }
}