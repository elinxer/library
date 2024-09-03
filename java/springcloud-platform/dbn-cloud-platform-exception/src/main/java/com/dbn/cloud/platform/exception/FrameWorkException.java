package com.dbn.cloud.platform.exception;

import com.dbn.cloud.platform.exception.entity.IErrorCode;

/**
 * 系统内部异常（仅限内部使用）
 *
 * @author elinx
 * @date 2021-08-17
 */
public class FrameWorkException extends RuntimeException {
    /**
     * 错误码
     */
    private final IErrorCode code;
    /**
     * 参数
     */
    private final Object[] params;

    /**
     * 构造器
     *
     * @param errorCode 错误码
     * @param params    参数
     */
    public FrameWorkException(IErrorCode errorCode, Object... params) {
        super();
        this.code = errorCode;
        this.params = params;
    }

    /**
     * 构造器
     *
     * @param cause     异常类
     * @param errorCode 错误码
     * @param params    参数
     */
    public FrameWorkException(Throwable cause, IErrorCode errorCode, Object... params) {
        super(cause);
        this.code = errorCode;
        this.params = params;
    }

    public String getCode() {
        return code.getCode();
    }

    public Object[] getParams() {
        return this.params;
    }

    public String getMessage() {
        return code.getMessage(params);
    }

    public Boolean hasCause() {
        return this.getCause() != null;
    }

}
