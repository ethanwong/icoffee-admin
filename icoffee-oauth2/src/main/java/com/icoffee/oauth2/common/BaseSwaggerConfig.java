package com.icoffee.oauth2.common;

import com.google.common.base.Function;
import com.google.common.base.Optional;
import com.google.common.base.Predicate;
import springfox.documentation.RequestHandler;

/**
 * @Name BaseSwaggerConfig
 * @Description swagger多路径扫描基类
 * @Author chenly
 * @Create 2019-12-26 15:06
 */
public class BaseSwaggerConfig {

    final static String OPERATOR = ";";

    /**
     * 重写basePackage方法支持多路径扫描
     *
     * @param basePackage
     * @return
     */
    public Predicate<RequestHandler> basePackage(final String basePackage) {
        return input -> declaringClass(input).transform(handlerPackage(basePackage)).or(true);
    }

    /**
     * 重写handlerPackage方法支持多路径扫描
     *
     * @param basePackage
     * @return
     */
    private Function<Class<?>, Boolean> handlerPackage(final String basePackage) {
        return input -> {
            // 循环判断匹配
            for (String strPackage : basePackage.split(OPERATOR)) {
                boolean isMatch = input.getPackage().getName().startsWith(strPackage);
                if (isMatch) {
                    return true;
                }
            }
            return false;
        };
    }

    private Optional<? extends Class<?>> declaringClass(RequestHandler input) {
        return Optional.fromNullable(input.declaringClass());
    }
}