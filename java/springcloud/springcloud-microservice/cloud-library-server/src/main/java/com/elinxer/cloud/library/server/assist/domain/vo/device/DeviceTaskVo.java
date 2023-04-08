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
@ApiModel(value = "DeviceTaskVo")
public class DeviceTaskVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "作业ID")
    private Long id;

    @ApiModelProperty(value = "设备编号")
    private String deviceNo;

    @ApiModelProperty(value = "作业编号")
    private String taskNo;

    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "开始时间")
    private String startAt;

    @ApiModelProperty(value = "作业面积")
    private double area;

    @ApiModelProperty(value = "作业时长")
    private double duration;

    @ApiModelProperty(value = "里程")
    private double mileage;


}
