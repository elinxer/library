package com.dbn.cloud.platform.exception;

import com.dbn.cloud.platform.exception.entity.ErrorCode;
import com.dbn.cloud.platform.exception.entity.IErrorCode;

/**
 * 应用层异常
 *
 * @author elinx
 */
public class AppException extends com.dbn.cloud.platform.exception.FrameWorkException {

    public AppException(IErrorCode errorCode, Object... params) {
        super(errorCode, params);
    }

    public AppException(Throwable cause, IErrorCode errorCode, Object... params) {
        super(cause, errorCode, params);
    }

    public AppException(String errCode, Object... params) {
        super(getErrorCode(errCode), params);
    }

    public static IErrorCode getErrorCode(String code) {
        // TODO
        return new ErrorCode("500", code);
    }

}
