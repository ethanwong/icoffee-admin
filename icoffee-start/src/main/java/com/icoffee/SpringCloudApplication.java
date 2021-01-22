package com.icoffee;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @Name SpringCloudApplication
 * @Description 系统从这里启动
 * @Author huangyingfeng
 * @Create 2021-01-21 15:39
 */
@SpringBootApplication
@MapperScan({"com.icoffee.oauth2.mapper"})
public class SpringCloudApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringCloudApplication.class, args);
    }

}
