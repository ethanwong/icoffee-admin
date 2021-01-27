package com.icoffee.security.web;

import com.icoffee.common.annotation.AnonymousGetMapping;
import com.icoffee.common.annotation.AnonymousPostMapping;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @Name PermissionTestApi
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-21 18:24
 */
@RestController
@RequestMapping("/api/permission")
public class PermissionTestApi {

    @PreAuthorize("permitAll()")
    @GetMapping("permitAll")
    public String testPermitAll(){
        return "测试 @PreAuthorize(\"permitAll()\") 生效！";
    }

    @PreAuthorize("isAnonymous()")
    @GetMapping("isAnonymous")
    public String testIsAnonymous(){
        return "测试 @PreAuthorize(\"isAnonymous()\") 生效！";
    }

    @PreAuthorize("true")
    @GetMapping("true")
    public String testTrue(){
        return "测试 @PreAuthorize(\"true\") 生效！";
    }

    @PreAuthorize("false")
    @GetMapping("false")
    public String testFalse(){
        return "测试 @PreAuthorize(\"false\") 生效！";
    }

    @PreAuthorize("isFullyAuthenticated()")
    @GetMapping("isFullyAuthenticated")
    public String testIsFullyAuthenticated(){
        return "测试 @PreAuthorize(\"isFullyAuthenticated\") 生效！";
    }


    @AnonymousGetMapping("test")
    public String testGet(){
        return "没有设置安全拦截的请求！";
    }

    @AnonymousPostMapping("test")
    public String testPost(){
        return "没有设置安全拦截的请求！";
    }

    @GetMapping("none")
    public String testNone(){
        return "没有设置安全拦截的请求！";
    }

}
