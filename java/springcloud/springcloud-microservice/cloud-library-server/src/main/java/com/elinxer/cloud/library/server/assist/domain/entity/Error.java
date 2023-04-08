package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.dbn.cloud.platform.web.crud.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("tbl_assist_app_errors")
public class Error extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "err_code")
    private String errCode;

    @TableField(value = "device_no")
    private String deviceNo;

    @TableField(value = "module")
    private String module;

    @TableField(value = "error_at")
    private String errorAt;

    @TableField(value = "level")
    private Integer level;

    @TableField(value = "message")
    private String message;

    @TableLogic
    private boolean deleted;

}
