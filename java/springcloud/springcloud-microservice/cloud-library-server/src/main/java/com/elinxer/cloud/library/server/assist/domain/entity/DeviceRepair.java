package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("tbl_assist_form_repair")
public class DeviceRepair {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "real_name")
    private String realName;

    @TableField(value = "device_no")
    private String deviceNo;

    @TableField(value = "user_id")
    private Long userId;

    @TableField(value = "device_id")
    private Long deviceId;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "address")
    private String address;

    @TableField(value = "repair_type")
    private String repairType;

    @TableField(value = "`desc`")
    private String desc;

    @TableField(value = "images")
    private String images;

    @TableField(value = "created_at")
    private String createdAt;

    @TableLogic
    private boolean deleted;

}
