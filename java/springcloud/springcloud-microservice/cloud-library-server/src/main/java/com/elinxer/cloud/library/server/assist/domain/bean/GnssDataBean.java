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
@ApiModel(value = "Gnss模块消息")
public class GnssDataBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "utc")
    private Double utc;

    @ApiModelProperty(value = "天线相位中心")
    private Double posLat;

    @ApiModelProperty(value = "天线相位中心")
    private Double posLon ;

    @ApiModelProperty(value = "天线相位中心")
    private Float posHeight ;

    @ApiModelProperty(value = "numSats")
    private Integer numSats;

    @ApiModelProperty(value = "hdop")
    private Float hdop;

    @ApiModelProperty(value = "diffAge")
    private Integer diffAge;

    @ApiModelProperty(value = "日")
    private Integer day;

    @ApiModelProperty(value = "月")
    private Integer month;

    @ApiModelProperty(value = "年")
    private Integer year;

    @ApiModelProperty(value = "时")
    private Integer hour;

    @ApiModelProperty(value = "分")
    private Integer minute;

    @ApiModelProperty(value = "秒")
    private Float second;

    @ApiModelProperty(value = "定位解状态")
    private String posQual;

    @ApiModelProperty(value = "定向解状态")
    private String headingQual;

    @ApiModelProperty(value = "baseline_length")
    private Float baselineLength;

    @ApiModelProperty(value = "hor_speed")
    private Float horSpeed;

    @ApiModelProperty(value = "hor_speed_dir ")
    private Float horSpeedDir;

    @ApiModelProperty(value = "tilt")
    private Float tilt;

    @ApiModelProperty(value = "heading")
    private Float heading;

}
