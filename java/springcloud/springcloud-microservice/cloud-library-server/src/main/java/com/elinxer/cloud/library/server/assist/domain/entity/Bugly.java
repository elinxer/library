package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dbn.cloud.platform.web.crud.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@TableName("tbl_assist_app_bugly")
public class Bugly extends BaseEntity {

    @TableField(value = "event_type")
    private String eventType;

    @TableField(value = "json_value")
    private String jsonValue;

}
