package com.dbn.cloud.platform.database.mybatis.properties;

import lombok.Getter;

/**
 * 多租户类型
 * <p>
 * NONE、COLUMN、SCHEMA
 */
@Getter
public enum TenantType {
    /**
     * 非租户模式
     */
    NONE("非租户模式"),
    /**
     * 字段模式
     * 在sql中拼接 tenant_code 字段
     */
    COLUMN("字段模式");

    private String describe;


    TenantType(String describe) {
        this.describe = describe;
    }

    public boolean eq(String val) {
        return this.name().equalsIgnoreCase(val);
    }

    public boolean eq(TenantType val) {
        if (val == null) {
            return false;
        }
        return eq(val.name());
    }
}
