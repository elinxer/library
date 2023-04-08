package com.elinxer.cloud.library.server.assist.aop;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
public @interface RequestLimit {
    /**
     * 请求次数
     */
    int count() default 1;

    /**
     * 限制时间（秒）
     */
    long time() default 5;
}
