package com.icoffee.oauth2.service;


import com.icoffee.oauth2.common.ResultDTO;
import com.icoffee.oauth2.model.UserDO;

/**
 * @Name UserService
 * @Description 用户模块Service接口
 * @Author chenly
 * @Create 2019-11-29 17:16
 */
public interface UserService extends MpBaseService<UserDO> {

    /**
     * 根据用户名称获取账号信息
     *
     * @param username
     * @return
     */
    UserDO getByUsername(String username);

    /**
     * 保存对象
     *
     * @param userDO 实体
     * @return
     */
    ResultDTO saveEntity(UserDO userDO);

    /**
     * 更新对象
     *
     * @param userDO 实体
     * @return
     */
    ResultDTO updateEntity(UserDO userDO);

    /**
     * 更新基础信息
     *
     * @param realname    真实名称
     * @param email       邮箱
     * @param phoneNumber 电话
     * @return
     */
    ResultDTO update(String realname, String email, String phoneNumber) throws Exception;

    /**
     * 修改密码
     *
     * @param oldPwd 旧密码
     * @param newPwd 新密码
     * @return
     */
    ResultDTO resetPwd(String oldPwd, String newPwd) throws Exception;

    /**
     * 改变用户锁定状态
     *
     * @param id 主键
     */
    ResultDTO changeLocked(String id) throws Exception;

}