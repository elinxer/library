package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dbn.cloud.platform.web.crud.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("tbl_assist_app_error_code")
public class ErrorCode extends BaseEntity {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "err_code")
    private String errCode;

    @TableField(value = "module")
    private String module;

    @TableField(value = "level")
    private Integer level;

    @TableField(value = "message")
    private String message;

    @TableField(value = "content")
    private String content;

}
