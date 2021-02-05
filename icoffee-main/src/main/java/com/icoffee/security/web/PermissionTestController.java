package com.icoffee.security.web;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name PermissionTestController
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2020-02-27 15:18
 */
@RestController
@RequestMapping("/api/auth")
public class PermissionTestController {

    @GetMapping(value = "/admin")
    public String admin() {
        return "hello admin";
    }

    @GetMapping(value = "/user")
    public String user() {
        return "hello user";
    }

    @GetMapping(value = "/coffee")
    public String coffee() {
        return "hello coffee";
    }




}
