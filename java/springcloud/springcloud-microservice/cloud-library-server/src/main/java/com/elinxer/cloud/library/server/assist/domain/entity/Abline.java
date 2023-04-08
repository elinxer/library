package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dbn.cloud.platform.web.crud.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("tbl_assist_app_abline")
public class Abline extends BaseEntity {

    @TableField(value = "name")
    private String name;

    @TableField(value = "`code`")
    private String code;

    @TableField(value = "`desc`")
    private String desc;

    @TableField(value = "device_code")
    private String deviceCode;

    @TableField(value = "params")
    private String params;

    @TableField(value = "point")
    private String point;

    @TableField(value = "location")
    private String location;

    @TableField(value = "width")
    private Double width;

    @TableField(value = "geometry")
    private String geometry;

    @TableField(value = "geo_hash")
    private String geoHash;

    @TableLogic
    private boolean deleted;
}
