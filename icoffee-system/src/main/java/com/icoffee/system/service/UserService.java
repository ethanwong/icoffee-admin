package com.icoffee.system.service;


import com.icoffee.common.dto.ResultDto;
import com.icoffee.common.service.MpBaseService;
import com.icoffee.system.domain.User;
import com.icoffee.system.dto.PermissionDto;

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
    ResultDto saveEntity(User userDO);

    /**
     * 更新对象
     *
     * @param userDO 实体
     * @return
     */
    ResultDto updateEntity(User userDO);

    /**
     * 更新基础信息
     *
     * @param realname    真实名称
     * @param email       邮箱
     * @param phoneNumber 电话
     * @return
     */
    ResultDto update(String realname, String email, String phoneNumber) throws Exception;

    /**
     * 修改密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return
     */
    ResultDto resetPwd(String oldPwd, String newPwd) throws Exception;

    /**
     * 改变用户锁定状态
     *
     * @param id 主键
     */
    ResultDto changeLocked(String id) throws Exception;

    /**
     * 获取用户授权信息
     *
     * @param username
     * @return
     */
    List<PermissionDto> getUserPermission(String username);
}