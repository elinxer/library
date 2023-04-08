package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
@TableName("tbl_assist_device_summary_log")
public class DeviceSummaryLog {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "device_no")
    private String deviceNo;

    @TableField(value = "task_no")
    private String taskNo;

    @TableField(value = "area")
    private double area;

    @TableField(value = "mileage")
    private double mileage;

    @TableField(value = "duration")
    private int duration;

    @TableField(value = "x")
    private String x;

    @TableField(value = "y")
    private String y;

    @TableField(value = "yaw")
    private String yaw;

    @TableField(value = "speed")
    private double speed;

    @TableField(value = "direction")
    private int direction;

    @TableField(value = "longitude")
    private String longitude;

    @TableField(value = "latitude")
    private String latitude;

}
