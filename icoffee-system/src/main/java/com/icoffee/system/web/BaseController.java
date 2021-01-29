package com.icoffee.system.web;

import com.icoffee.system.domain.User;
import com.icoffee.system.service.RoleService;
import com.icoffee.system.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;


/**
 * @Name BaseController
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-25 16:24
 */
public class BaseController {


    @Autowired
    private UserService userService;

    @Autowired
    private RoleService roleService;

    public User getAccount() {
        String currentUsername = "";
        return userService.getByUsername(currentUsername);
    }

    public String getCompanyCode() {

        return null;
    }
}
