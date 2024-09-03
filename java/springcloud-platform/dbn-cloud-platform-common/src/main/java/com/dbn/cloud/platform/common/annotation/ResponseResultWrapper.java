package com.dbn.cloud.platform.common.annotation;

import java.lang.annotation.*;

/**
 * 用来标记controller接口返回是否需要包装的注解
 *
 * @author elinx
 */

@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD})
@Documented
public @interface ResponseResultWrapper {
}
