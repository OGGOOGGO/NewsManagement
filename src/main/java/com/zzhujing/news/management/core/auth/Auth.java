package com.zzhujing.news.management.core.auth;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @auther hujing
 * @date Create in 2020/6/2
 * Copyright 本内容仅限于杭州大希地有限公司内部传阅，禁止外泄以及用于其他的商业目的
 **/
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD, ElementType.TYPE})
public @interface Auth {
    Role[] value() default Role.ADMIN;
}
