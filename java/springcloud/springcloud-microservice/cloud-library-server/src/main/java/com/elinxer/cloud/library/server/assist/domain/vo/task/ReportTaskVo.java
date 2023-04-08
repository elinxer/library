package com.elinxer.cloud.library.server.assist.domain.vo.task;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "ReportTaskVo")
public class ReportTaskVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "设备编号")
    @NotEmpty(message = "deviceNo必填")
    private String deviceNo;

    @ApiModelProperty(value = "作业编号")
    @NotEmpty(message = "taskNo必填")
    private String taskNo;

    @ApiModelProperty(value = "作业名称")
    @NotEmpty(message = "name必填")
    private String name;

    @ApiModelProperty(value = "开始时间")
    @NotEmpty(message = "startAt必填")
    private String startAt;

    @ApiModelProperty(value = "作业参数")
    private String params;

    @ApiModelProperty(value = "备注")
    private String remark;

    @ApiModelProperty(value = "当前时间")
    @NotEmpty(message = "timestamp必填")
    private String timestamp;


}
