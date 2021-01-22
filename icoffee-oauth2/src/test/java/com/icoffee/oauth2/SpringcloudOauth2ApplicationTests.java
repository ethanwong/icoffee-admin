package com.icoffee.oauth2;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;

@SpringBootTest
class SpringcloudOauth2ApplicationTests {

    @Autowired
    public PasswordEncoder passwordEncoder;

    void contextLoads() {
        //client-id coffee-client对应密码的加密
       String secret = passwordEncoder.encode("6a17670baa0c4a929d16ef69e45f2d57");
        System.out.println(secret);
    }

}
