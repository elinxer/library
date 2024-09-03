package com.dbn.cloud.platform.security.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

/**
 * 用户实体
 *
 * @author elinx
 */
@Data
@TableName("sys_user")
public class SysUser implements Serializable {

    private static final long serialVersionUID = -5886012896705137070L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)  //雪花算法  id生成策略
    private Long id;
    private String username;
    private String password;
    @TableField(value = "nick_name")
    private String nickname;
    @TableField(value = "head_img_url")
    private String headImgUrl;
    private String phone;
    private Integer sex;
    private Integer status;
    private String type;
    @TableField(value = "company_id")
    private Long companyId;

    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;

    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;

    @TableField(exist = false)
    private List<SysRole> roles;

    @TableField(exist = false)
    private String roleId;

    @TableField(exist = false)
    private String oldPassword;
    @TableField(exist = false)
    private String newPassword;

    @TableField(exist = false)
    private Long owner;

    @TableField(exist = false)
    private String openId;

}
