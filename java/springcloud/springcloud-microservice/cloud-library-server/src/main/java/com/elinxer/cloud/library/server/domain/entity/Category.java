package com.elinxer.cloud.library.server.domain.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dbn.cloud.platform.web.crud.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("library_category")
public class Category {

    @TableId(value = "id", type = IdType.ASSIGN_ID)
    private Long id;

    @TableField(value = "pid")
    private String pid;

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "name_en")
    private String nameEn;

    @TableField(value = "weight")
    private int weight;

    @TableField(value = "type")
    private String type;

    @TableField(value = "is_deleted")
    private String isDeleted;

}
