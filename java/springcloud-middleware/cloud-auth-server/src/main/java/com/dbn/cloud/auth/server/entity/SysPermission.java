package com.dbn.cloud.auth.server.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;


/**
 * 类说明 权限标识
 */
@Data
@TableName("sys_permission")
public class SysPermission implements Serializable {

    private static final long serialVersionUID = 1389727646460449239L;
    @TableId(value = "id", type = IdType.ASSIGN_ID)  //雪花算法  id生成策略
    private Long id;
    private String permission;
    private String name;
    @TableField(value = "create_time", fill = FieldFill.INSERT)
    private Date createTime;
    @TableField(value = "update_time", fill = FieldFill.INSERT_UPDATE)
    private Date updateTime;
    @TableField(exist = false)
    private Long roleId;


    @TableField(exist = false)
    private Set<Long> authIds;

}
