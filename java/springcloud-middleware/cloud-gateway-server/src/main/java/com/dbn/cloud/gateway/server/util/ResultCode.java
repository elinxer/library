package com.dbn.cloud.gateway.server.util;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

/**
 * ResultCode
 */
@AllArgsConstructor
@NoArgsConstructor
public enum ResultCode {

    SYSTEM_EXECUTION_ERROR("B0001", "系统执行出错"),
    TOKEN_INVALID_OR_EXPIRED("A0230", "token无效或已过期"),
    TOKEN_ACCESS_FORBIDDEN("A0231", "token已被禁止访问"),
    AUTHORIZED_ERROR("A0300", "访问权限异常"),
    ACCESS_UNAUTHORIZED("A0301", "访问API接口未授权");

    public String getCode() {
        return code;
    }

    public String getMsg() {
        return msg;
    }

    private String code;

    private String msg;

    public static ResultCode getValue(String code) {
        for (ResultCode value : values()) {
            if (value.getCode().equals(code)) {
                return value;
            }
        }
        return SYSTEM_EXECUTION_ERROR; // 默认系统执行错误
    }
}
