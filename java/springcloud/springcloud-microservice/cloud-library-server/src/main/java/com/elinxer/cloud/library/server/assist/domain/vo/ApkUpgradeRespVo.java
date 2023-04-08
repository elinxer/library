package com.elinxer.cloud.library.server.assist.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ApkUpgradeVo")
public class ApkUpgradeRespVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "最新版本")
    private ApkVo upgrade;

    @ApiModelProperty(value = "版本列表")
    private List<ApkVo> upgradeList;


}
