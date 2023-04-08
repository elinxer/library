package com.elinxer.cloud.library.server.assist.domain.vo.device;

import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
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
@ApiModel(value = "DeviceUserVo")
public class DeviceUserVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "主键id", required = false)
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "名称", required = true)
    private String name;

    @ApiModelProperty(value = "型号编码")
    private String code;

    @ApiModelProperty(value = "型号描述")
    private String desc;

    @ApiModelProperty(value = "brandName")
    private String brandName;

    @ApiModelProperty(value = "typeName")
    private String typeName;

    @ApiModelProperty(value = "设备序列号")
    @NotEmpty(message = "设备序列号必填")
    private String serialNo;

    @ApiModelProperty(value = "联系方式")
    private String contact;

    @ApiModelProperty(value = "图标")
    private String icon;

    @ApiModelProperty(value = "图片")
    private String image;

    @ApiModelProperty(value = "上线时间")
    private String onlineDate;

    @ApiModelProperty(value = "激活时间")
    private String activeDate;

    @ApiModelProperty(value = "设备状态： 0离线，1在线")
    private int state;

    @ApiModelProperty(value = "作业状态：0空闲，1作业中")
    private int taskState;

    @ApiModelProperty(value = "总作业面积")
    private double area;

    @ApiModelProperty(value = "总作业时长")
    private int duration;

    @ApiModelProperty(value = "总作业里程")
    private int mileage;

    @ApiModelProperty(value = "天数")
    private int day;

}
