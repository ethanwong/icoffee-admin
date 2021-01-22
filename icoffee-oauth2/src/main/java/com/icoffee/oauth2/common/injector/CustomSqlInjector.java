package com.icoffee.oauth2.common.injector;

import com.baomidou.mybatisplus.core.injector.AbstractMethod;
import com.baomidou.mybatisplus.core.injector.DefaultSqlInjector;

import java.util.List;

/**
 * @Name MyLogicSqlInjector
 * @Description 自定义sql注入器
 * @Author chenly
 * @Create 2019-12-10 15:29
 */
public class CustomSqlInjector extends DefaultSqlInjector {


    @Override
    public List<AbstractMethod> getMethodList(Class<?> mapperClass) {
        List<AbstractMethod> methodList = super.getMethodList(mapperClass);
        //增加deleteAll方法
        methodList.add(new DeleteAll());
        //增加selectAll方法
        methodList.add(new SelectAll());
        return methodList;
    }
}