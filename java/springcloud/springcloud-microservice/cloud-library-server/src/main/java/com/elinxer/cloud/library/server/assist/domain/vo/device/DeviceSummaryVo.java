package com.elinxer.cloud.library.server.assist.domain.vo.device;

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
@ApiModel(value = "DeviceSummaryVo")
public class DeviceSummaryVo implements Serializable {

    private static final long serialVersionUID = 1L;

    private String deviceNo;

    private String taskNo;

    @ApiModelProperty(value = "总作业面积")
    private double area;

    @ApiModelProperty(value = "总作业时长")
    private int duration;

    @ApiModelProperty(value = "总里程")
    private double mileage;

}
