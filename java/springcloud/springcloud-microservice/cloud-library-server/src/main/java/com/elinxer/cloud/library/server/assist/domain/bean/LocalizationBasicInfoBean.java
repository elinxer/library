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
@ApiModel(value = "定位模块消息")
public class LocalizationBasicInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "控制中心实时速度朝向")
    private Integer  rtkVelDirection;

    @ApiModelProperty(value = "车辆停车状态")
    private Integer  vehicleStopStatus;

    @ApiModelProperty(value = "定位融合解状态")
    private Integer  msfStatus;

    @ApiModelProperty(value = "控制中心实时速度")
    private Float rtkVel ;

    @ApiModelProperty(value = "控制中心航向角")
    private Float yaw;

    @ApiModelProperty(value = "控制中心俯仰角")
    private Float pitch;

    @ApiModelProperty(value = "控制中心横滚角")
    private Float roll;

    @ApiModelProperty(value = "控制中心东坐标")
    private Double x;

    @ApiModelProperty(value = "控制中心北坐标")
    private Double y;

    @ApiModelProperty(value = "控制中心天坐标")
    private Double z;

    @ApiModelProperty(value = "控制中心经度")
    private Double longitude;

    @ApiModelProperty(value = "控制中心纬度")
    private Double latitude;

    @ApiModelProperty(value = "控制中心高程")
    private Double height;

}
