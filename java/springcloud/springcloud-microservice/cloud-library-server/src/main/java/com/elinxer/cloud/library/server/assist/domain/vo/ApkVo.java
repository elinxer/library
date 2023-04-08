package com.elinxer.cloud.library.server.assist.domain.vo;

import com.dbn.cloud.platform.validation.validator.group.UpdateGroup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "apk管理")
public class ApkVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = {UpdateGroup.class})
    @ApiModelProperty(value = "主键id，修改时必填，新增时不需要填", required = false)
    @Max(value = Long.MAX_VALUE, message = "id不能大于9223372036854775807")
    @Min(value = 0, message = "id不能小于0")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "下载地址")
    private String downloadUrl;

    @ApiModelProperty(value = "apk MD5")
    private String apkMd5;

    @ApiModelProperty(value = "当前升级包版本")
    private Integer apkVersion;

    @ApiModelProperty(value = "版本号描述")
    private String apkVersionDesc;

    @ApiModelProperty(value = "指定更新版本")
    private String updateVersion;

    @ApiModelProperty(value = "制更新1是，0否")
    private Boolean forceUpdate;

}
