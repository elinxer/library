package com.dbn.cloud.platform.exception.constant;

import com.dbn.cloud.platform.exception.entity.ErrorCode;
import com.dbn.cloud.platform.exception.entity.IErrorCode;

/**
 * 系统异常常量
 *
 * @author elinx
 * @since 1.0
 */

public enum ErrorEnum implements IErrorCode {

    PLAT_COMMON_0001("feign调用回传结构异常");

    private String message;

    private final ErrorCode errorCode;

    private ErrorEnum(String messaage) {
        this.message = messaage;
        errorCode = new ErrorCode(this.name(), this.message);
    }

    @Override
    public String getCode() {
        return errorCode.getCode();
    }

    @Override
    public String getMessage(Object... params) {
        return errorCode.getMessage(params);
    }
}
