package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.util.Date;

@Data
@TableName("tbl_assist_user")
public class User {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "username")
    private String username;

    @TableField(value = "password")
    private String password;

    @TableField(value = "nickname")
    private String nickname;

    @TableField(value = "real_name")
    private String realName;

    @TableField(value = "phone")
    private String phone;

    @TableField(value = "gender")
    private String gender;

    @TableField(value = "language")
    private String language;

    @TableField(value = "city")
    private String city;

    @TableField(value = "province")
    private String province;

    @TableField(value = "country")
    private String country;

    @TableField(value = "avatar_url")
    private String avatarUrl;

    @TableField(value = "open_id")
    private String openId;

    @TableField(value = "union_id")
    private String unionId;

    @TableField(value = "created_at", fill = FieldFill.INSERT)
    private Date createdAt;

    @TableField(value = "updated_at", fill = FieldFill.INSERT_UPDATE)
    private Date updatedAt;

    @TableField(value = "status")
    private Integer status;

    @TableField(value = "address")
    private String address;

    @TableLogic
    private boolean deleted;

}
