package com.dbn.cloud.platform.validation.constant;


import com.dbn.cloud.platform.exception.entity.IErrorCode;

/**
 * 检查器自动装配类
 *
 * @author elinx
 * @version 1.0.0
 */
public enum ValidationErrorEnum implements IErrorCode {

    PLAT_VALID_0001("存在相同数据");

    private String message;

    private ValidationErrorEnum(String message) {
        this.message = message;
    }

    @Override
    public String getCode() {
        return null;
    }

    @Override
    public String getMessage(Object... params) {
        return null;
    }
}
