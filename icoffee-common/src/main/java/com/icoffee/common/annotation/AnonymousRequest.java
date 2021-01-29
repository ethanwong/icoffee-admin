package com.icoffee.common.annotation;

import java.lang.annotation.*;

/**
 * @Name AnonymousRequest
 * @Description
 * @Author huangyingfeng
 * @Create 2021-01-21 15:39
 */
@Inherited
@Documented
@Target({ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface AnonymousRequest {

}
