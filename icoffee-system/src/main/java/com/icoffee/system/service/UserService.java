package com.icoffee.system.service;


import com.icoffee.common.dto.AuthorityDto;
import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.User;

import java.util.List;

/**
 * @Name UserService
 * @Description 用户模块Service接口
 * @Author chenly
 * @Create 2019-11-29 17:16
 */
public interface UserService extends MpBaseService<User> {

    /**
     * 根据用户名称获取账号信息
     *
     * @param username
     * @return
     */
    User getByUsername(String username);

    /**
     * 保存对象
     *
     * @param userDO 实体
     * @return
     */
    ResultDto saveUser(User userDO);

    /**
     * 更新对象
     *
     * @param userDO 实体
     * @return
     */
    ResultDto updateUser(User userDO);

    /**
     * 根据用户角色获取用户信息
     *
     * @param roleId
     * @return
     */
    List<User> getUserListByRoleId(String roleId);

    /**
     * 修改密码
     *
     * @param username
     * @param password
     * @return
     */
    ResultDto resetPassword(String username, String password);

    /**
     * 获取用户授权信息
     *
     * @param roleId
     * @return
     */
    List<AuthorityDto> getUserGrantedAuthorities(String roleId);
}