package com.icoffee.common.config;

import com.icoffee.common.mybatis.injector.CustomSqlInjector;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * @Name MybatisPlusConfig
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2019-11-18 18:18
 */
@EnableTransactionManagement
@Configuration
public class MybatisPlusConfig {

    /**
     * 自定义SqlInjector
     */
    @Bean
    public CustomSqlInjector customSqlInjector() {
        return new CustomSqlInjector();
    }


}
