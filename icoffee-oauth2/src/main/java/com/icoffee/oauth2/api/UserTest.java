package com.icoffee.oauth2.api;

import com.icoffee.oauth2.annotation.PreAuthorizeExtend;
import com.icoffee.oauth2.aspect.AuthorizePoint;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name UserController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2020-02-27 15:18
 */
@AuthorizePoint("用户管理")
@RestController
@RequestMapping("/api/test")
public class UserTest {

    @AuthorizePoint("查看管理员账号")
    @PreAuthorize("hasAuthority('/api/user/admin')")
//    @PreAuthorizeExtend(model = "查看管理员账号",value = "hasAuthority('/api/user/admin')")
    @GetMapping(value = "/admin")
    public String admin() {
        return "hello admin";
    }

    @AuthorizePoint("查看用户账号")
    @PreAuthorize("hasAuthority('/api/user/user')")
    @GetMapping(value = "/user")
    public String user() {
        return "hello user";
    }

    @AuthorizePoint("查看咖啡账号")
    @PreAuthorize("hasAuthority('/api/user/coffee')")
    @GetMapping(value = "/coffee")
    public String coffee() {
        return "hello coffee";
    }

    @GetMapping(value = "/limit")
    public String noLimit(){
        return "no limit";
    }
}
