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
@ApiModel(value = "Ahrs模块消息")
public class AhrsDataBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "accx")
    private Float accx;

    @ApiModelProperty(value = "accy")
    private Float accy;

    @ApiModelProperty(value = "accz")
    private Float accz ;

    @ApiModelProperty(value = "gyrox")
    private Float gyrox ;

    @ApiModelProperty(value = "gyroy")
    private Float gyroy;

    @ApiModelProperty(value = "gyroz ")
    private Float gyroz ;

    @ApiModelProperty(value = "magx")
    private Float magx;

    @ApiModelProperty(value = "magy")
    private Float magy ;

    @ApiModelProperty(value = "magz")
    private Float magz ;

    @ApiModelProperty(value = "pressure")
    private Float pressure;

    @ApiModelProperty(value = "temp")
    private Float temp;

    @ApiModelProperty(value = "roll")
    private Float roll ;

    @ApiModelProperty(value = "pitch")
    private Float pitch ;

    @ApiModelProperty(value = "yaw")
    private Float yaw;

}
