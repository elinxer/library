package com.dbn.cloud.platform.database.mybatis.enums;

/**
 * 删除标识枚举
 */
public enum DelFlagEnum {
    /**
     * 正常状态
     */
    NORMAL(0),
    /**
     * 删除状态
     */
    DEL(1);

    private int value;

    DelFlagEnum(int value) {
        this.value = value;
    }

    public int value() {
        return this.value;

    }

}
