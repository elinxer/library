package com.elinxer.cloud.library.server.assist.domain.bean;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "板卡模块消息")
public class GnssAhrsBasicInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "板卡信息")
    private AhrsDataBean  ahrs;

    @ApiModelProperty(value = "天线信息")
    private GnssDataBean  gnss;

    @ApiModelProperty(value = "差分源状态信息")
    private DiffStatusBean  diff;



}
