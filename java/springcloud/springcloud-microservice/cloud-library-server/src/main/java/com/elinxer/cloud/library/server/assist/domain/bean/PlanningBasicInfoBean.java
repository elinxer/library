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
@ApiModel(value = "决策模块消息")
public class PlanningBasicInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "作业编号")
    private String  taskId;

    @ApiModelProperty(value = "任务状态 开始/暂停/结束")
    private Integer  taskStatus;

    @ApiModelProperty(value = "作业index")
    private Integer  taskIndex;

    @ApiModelProperty(value = "农机具作业状态 1未作业 2正在作业")
    private Integer  implementWorkState;

    @ApiModelProperty(value = "作模式 单机2 多/主3 多/从4")
    private Integer  workModeState;

    @ApiModelProperty(value = "自动驾驶自检状态")
    private Integer autopilotCheckState ;

    @ApiModelProperty(value = "自动驾驶状态")
    private Integer autopilotState;

    @ApiModelProperty(value = "移动状态 1静止，底盘未移动 2移动状态 ")
    private Integer moveState ;

    @ApiModelProperty(value = "当前任务下，地盘行驶的总里程")
    private Float curtaskTotalMileage ;

    @ApiModelProperty(value = "当前任务下，总的已作业面积")
    private Float curtaskTotalWorkarea;

    @ApiModelProperty(value = "映射点总里程")
    private Float curtaskTotalMappingMileage ;

    @ApiModelProperty(value = "累计作业面积")
    private Float cumulatinveArea ;

    @ApiModelProperty(value = "当前任务下，已完成任务百分比")
    private Float workCompletionPercent;

}
