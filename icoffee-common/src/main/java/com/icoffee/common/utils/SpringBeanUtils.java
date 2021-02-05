package com.icoffee.common.utils;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

/**
 * @Name SpringBeanUtils
 * @Description Spring Bean工具类
 * @Author chenly
 * @Create 2020-08-17 10:04
 */
@Log4j2
@Component
public class SpringBeanUtils implements ApplicationContextAware {

    /**
     * 应用上下文
     */
    private static ApplicationContext applicationContext;

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        SpringBeanUtils.applicationContext = applicationContext;
    }

    public static ApplicationContext getApplicationContext() {
        return applicationContext;
    }
}