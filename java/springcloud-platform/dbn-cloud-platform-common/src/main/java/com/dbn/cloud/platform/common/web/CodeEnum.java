package com.dbn.cloud.platform.common.web;

/**
 * 响应标志字典
 *
 * @author elinx
 * @since 1.0
 */
public enum CodeEnum {
    SUCCESS(0),
    ERROR(1);

    private Integer code;

    CodeEnum(Integer code) {
        this.code = code;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }
}
