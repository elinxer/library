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
@ApiModel(value = "控制模块消息")
public class ControlBasicInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "位置到达标志，1：到达，0：未到达")
    private Integer  arriveState;

    @ApiModelProperty(value = "控制偏差")
    private Float controlDev ;

    @ApiModelProperty(value = "上线状态  0未上线 1上线完成")
    private Integer onlineState;

    @ApiModelProperty(value = "油门")
    private Integer accelerator;

    @ApiModelProperty(value = "转向")
    private Float steering ;

    @ApiModelProperty(value = "主从机控制偏差y 单位cm")
    private Float control_devy;

    @ApiModelProperty(value = "主从机控制偏差 x 单位cm")
    private Float control_devx ;

    @ApiModelProperty(value = "主机master vel")
    private Float masterVel ;
    
    @ApiModelProperty(value = "从机slave vel")
    private Float slaveVel;

}
