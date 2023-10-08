package com.bzz.common.annotation;


import java.lang.annotation.*;

/**
 * @Author : yang qianli
 * @email: 624003618@qq.com
 * @Date: 2019-05-21 02-00
 * @Modified by:
 * @Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE,ElementType.METHOD})
@Documented
public @interface FieldsInclude {

    Class returnType();

    String include();

}
