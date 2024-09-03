package com.dbn.cloud.platform.validation.constraint;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.*;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * 自定义身份证号码正则验证注解
 *
 * @author elinx
 * @version 1.0.0
 */
@Documented
@Constraint(validatedBy = {com.dbn.cloud.platform.validation.constraint.IdCardValidator.class})
@Target({METHOD, FIELD, ANNOTATION_TYPE, CONSTRUCTOR, PARAMETER})
@Retention(RUNTIME)
public @interface IdCard {
    String message() default "请输入有效的身份证号码";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

}
