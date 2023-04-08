package com.elinxer.cloud.library.server.assist.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ApkUpgradeVo")
public class ApkUpgradeVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "版本号", required = true)
    @NotNull(message = "版本号必填")
    private Integer apkVersion;


    @ApiModelProperty(value = "设备序列号", required = true)
    @NotBlank(message = "设备序列号必填")
    private String serialNo;

    @ApiModelProperty(value = "版本号(无需填写)", required = true)
    private String apkVersionStr;


}
