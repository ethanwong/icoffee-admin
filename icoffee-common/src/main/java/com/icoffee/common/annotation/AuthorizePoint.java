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
     * 模块名称
     *
     * @return
     */
    String value();

    /**
     * 模块编码
     *
     * @return
     */
    String code() default "";

    /**
     * URI
     *
     * @return
     */
    String uri() default "";
}
