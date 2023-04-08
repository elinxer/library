package com.elinxer.cloud.library.server.assist.domain.vo.device;

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
@ApiModel(value = "HomeSummaryVo")
public class HomeSummaryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "总作业面积")
    private double totalArea;

    @ApiModelProperty(value = "总作业时长")
    private double totalDuration;

    @ApiModelProperty(value = "总里程")
    private double totalMileage;

    @ApiModelProperty(value = "作业数")
    private int totalTask;

    @ApiModelProperty(value = "设备总数")
    private int totalDevice;

    @ApiModelProperty(value = "设备在线总数")
    private int totalOnlineDevice;

    @ApiModelProperty(value = "当前日期")
    private String curDate;

    @ApiModelProperty(value = "所有设备信息")
    List<DeviceSnapshotVo> deviceSnapshots;


}
