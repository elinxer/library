package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dbn.cloud.platform.web.crud.entity.BaseEntity;
import lombok.Builder;
import lombok.Data;

import java.util.Date;

@Data
@Builder
@TableName("tbl_assist_app_operation_log")
public class OperationLog extends BaseEntity {

    @TableField(value = "operation_code")
    private String operationCode;

    @TableField(value = "device_code")
    private String deviceCode;

    @TableField(value = "operation_status")
    private Integer operationStatus;

    @TableField(value = "`type`")
    private String type;

    @TableField(value = "json_value")
    private String jsonValue;

    @TableField(value = "report_time")
    private Date reportTime;

    @TableField(value = "x")
    private Double x;

    @TableField(value = "y")
    private Double y;

    @TableField(value = "rtk_status")
    private Integer rtkStatus;

    @TableField(value = "speed")
    private Float speed;

    @TableField(value = "control_dev")
    private Float controlDev;

    @TableLogic
    private boolean deleted;
}
