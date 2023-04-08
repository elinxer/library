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
@ApiModel(value = "作业工况消息")
public class OperationMsgBean implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "天线板卡")
    private GnssAhrsBasicInfoBean gnssAhrs;

    @ApiModelProperty(value = "定位模块")
    private LocalizationBasicInfoBean localization;

    @ApiModelProperty(value = "控制模块")
    private ControlBasicInfoBean control;

    @ApiModelProperty(value = "其他")
    private CommBasicInfoBean comm;

    @ApiModelProperty(value = "决策")
    private PlanningBasicInfoBean planning;

}
