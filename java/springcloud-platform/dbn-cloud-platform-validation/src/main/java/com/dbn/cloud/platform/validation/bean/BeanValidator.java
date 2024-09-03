package com.dbn.cloud.platform.validation.bean;

import com.dbn.cloud.platform.validation.constant.ValidationErrorEnum;
import com.dbn.cloud.platform.validation.exception.ValidationException;
import com.dbn.cloud.platform.validation.validate.SimpleValidateResults;
import lombok.extern.slf4j.Slf4j;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.Set;

/**
 * Bean检查器
 *
 * @author elinx
 */
@Slf4j
public final class BeanValidator {

    private BeanValidator() {
    }

    static volatile Validator validator;

    public static Validator getValidator() {
        if (validator == null) {
            synchronized (BeanValidator.class) {
                ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
                return validator = factory.getValidator();
            }
        }
        return validator;
    }

    public static <T> T tryValidate(T bean, Class... group) {
        Set<ConstraintViolation<T>> violations = getValidator().validate(bean, group);
        if (!violations.isEmpty()) {
            SimpleValidateResults results = new SimpleValidateResults();
            for (ConstraintViolation<T> violation : violations) {
                results.addResult(violation.getPropertyPath().toString(), violation.getMessage());
            }
            throw new ValidationException(ValidationErrorEnum.PLAT_VALID_0001, results);
        }
        return bean;
    }
}
