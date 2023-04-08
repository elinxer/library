package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dbn.cloud.platform.web.crud.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("tbl_devices")
public class Device extends BaseEntity {

    @TableField(value = "`name`")
    private String name;

    @TableField(value = "brand_id")
    private Long brandId;

    @TableField(value = "type_id")
    private Long typeId;

    @TableField(value = "model_id")
    private Long modelId;

    @TableField(value = "`code`")
    private String code;

    @TableField(value = "`desc`")
    private String desc;

    @TableField(value = "param_group_id")
    private Long paramGroupId;

    @TableField(value = "category")
    private String category;

    @TableField(value = "serial_no")
    private String serialNo;

    @TableField(value = "ori_serial_no")
    private String oriSerialNo;

    @TableField(value = "cover_img")
    private String coverImg;

    @TableField(value = "warehouse_in")
    private String warehouseIn;

    @TableField(value = "warehouse_out")
    private String warehouseOut;

    @TableField(value = "client_id")
    private String clientId;

    @TableField(value = "secret_key")
    private String secretKey;

    @TableField(value = "state")
    private Integer state;

    @TableField(value = "state_at")
    private String stateAt;

    @TableField(value = "live_at")
    private String liveAt;

}
