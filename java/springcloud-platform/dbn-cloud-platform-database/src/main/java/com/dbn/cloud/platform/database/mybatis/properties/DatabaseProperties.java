package com.dbn.cloud.platform.database.mybatis.properties;


import com.baomidou.mybatisplus.annotation.DbType;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;


/**
 * mybatis
 */
@Data
@NoArgsConstructor
@ConfigurationProperties(prefix = "mybatis-plus")
public class DatabaseProperties {
    /**
     * 是否启用 防止全表更新与删除插件
     */
    private Boolean isBlockAttack = false;
    /**
     * 是否启用  sql性能规范插件
     */
    private Boolean isIllegalSql = false;
    /**
     * 是否p6spy在控制台打印日志
     */
    private Boolean p6spy = false;

    /**
     * 分页大小限制
     */
    private long maxLimit = -1;

    private DbType dbType = DbType.MYSQL;

    /**
     * 是否禁止写入
     */
    private Boolean isNotWrite = false;
    /**
     * 是否启用数据权限
     */
    private Boolean isDataScope = true;

    /**
     * 租户库 前缀
     */
    private String tenantDatabasePrefix = "dbn_tenant";

    /**
     * 租户开关，默认是false不开启 TRUE开启
     */
    private Boolean tenantEnable = false;
    /**
     * 租户id 列名
     */
    private String tenantIdColumn = "company_id";
    /**
     * 在执行sql时，忽略 租户插件自动拼接租户编码的表
     */
    private List<String> ignoreTenantTables = new ArrayList<>();

}
