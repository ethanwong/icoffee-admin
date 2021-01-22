package com.icoffee.oauth2.annotation;

import org.springframework.core.annotation.AliasFor;
import org.springframework.security.access.prepost.PreAuthorize;

import java.lang.annotation.*;

/**
 * @Name PreAuthorizeExtend
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-21 15:39
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Inherited
@Documented
@PreAuthorize("hasAuthority('')")
public @interface PreAuthorizeExtend {

    String model() default "";

    /**
     * Alias for {@link PreAuthorize#value}.
     */
    @AliasFor(annotation = PreAuthorize.class )
    String value() default "";

}
