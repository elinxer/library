package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("tbl_assist_task")
public class Task {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "device_no")
    private String deviceNo;

    @TableField(value = "task_no")
    private String taskNo;

    @TableField(value = "name")
    private String name;

    @TableField(value = "start_at")
    private String startAt;

    @TableField(value = "end_at")
    private String endAt;

    @TableField(value = "area")
    private double area;

    @TableField(value = "mileage")
    private double mileage;

    @TableField(value = "duration")
    private int duration;

    @TableField(value = "params")
    private String params;

    @TableField(value = "remark")
    private String remark;

    @TableLogic
    private Integer deleted;

}
