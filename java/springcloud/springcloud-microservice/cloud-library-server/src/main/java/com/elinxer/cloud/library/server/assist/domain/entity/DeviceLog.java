package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dbn.cloud.platform.web.crud.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("tbl_assist_device_summary")
public class DeviceLog extends BaseEntity {


    @TableField(value = "device_id")
    private Long deviceId;

    @TableField(value = "device_no")
    private String deviceNo;

    @TableField(value = "task_no")
    private String taskNo;

    @TableField(value = "area")
    private Double area;

    @TableField(value = "mileage")
    private Double mileage;

    @TableField(value = "duration")
    private Double duration;

    @TableField(value = "x")
    private String x;

    @TableField(value = "y")
    private String y;

    @TableField(value = "经度")
    private String longitude;

    @TableField(value = "纬度")
    private String latitude;

    @TableField(value = "速度")
    private Double speed;

    @TableField(value = "驾驶模式")
    private Integer driverMode;

    @TableField(value = "任务状态")
    private Integer taskState;

}
