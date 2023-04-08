package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

@Data
@TableName("tbl_assist_user_devices")
public class UserDevice {

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

    @TableField(value = "`status`")
    private Integer status;

    @TableField(value = "`active_at`")
    private String activeAt;

    @TableLogic
    private boolean deleted;

}
