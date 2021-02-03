package com.icoffee.common.annotation;


import java.lang.annotation.*;

/**
 * @Name AuthorizePoint
 * @Description ${DESCRIPTION}
 * @Author huangyingfeng
 * @Create 2020-02-27 16:22
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface AuthorizePoint {
    /**
     * 资源名称
     *
     * @return
     */
    String name();

    /**
     * 归属模块
     *
     * @return
     */
    String module();

    /**
     * 权限标识
     *
     * @return
     */
    String permission();

    /**
     * 资源的URI
     *
     * @return
     */
    String uri() default "";
}
