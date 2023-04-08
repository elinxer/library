package com.elinxer.cloud.library.server.assist.domain.entity;

import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableLogic;
import com.baomidou.mybatisplus.annotation.TableName;
import com.dbn.cloud.platform.web.crud.entity.BaseEntity;
import lombok.Data;

@Data
@TableName("tbl_assist_app_apks")
public class Apk extends BaseEntity {

    @TableField(value = "name")
    private String name;

    @TableField(value = "`code`")
    private String code;

    @TableField(value = "`desc`")
    private String desc;

    @TableField(value = "download_url")
    private String downloadUrl;

    @TableField(value = "apk_md5")
    private String apkMd5;

    @TableField(value = "apk_version")
    private String apkVersion;

    @TableField(value = "update_version")
    private String updateVersion;

    @TableField(value = "force_update")
    private Boolean forceUpdate;

    @TableLogic
    private boolean deleted;
}
