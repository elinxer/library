package com.elinxer.cloud.library.server.assist.domain.vo.repair;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DeviceRepairVo")
public class DeviceRepairVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户ID")
    @NotNull(message = "用户ID必填")
    private Long userId;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickname;

    @ApiModelProperty(value = "真实姓名")
    private String realName;

    @ApiModelProperty(value = "电话")
    private String phone;

    @ApiModelProperty(value = "状态:0 未处理，1已接受，2已处理，4已结束")
    private Integer status;

    @ApiModelProperty(value = "联系地址")
    private String address;

    @ApiModelProperty(value = "类型")
    @NotEmpty(message = "类型必填")
    private String repairType;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "图集")
    private String images;

    @ApiModelProperty(value = "设备编码")
    @NotEmpty(message = "设备编码必填")
    private String deviceNo;

    @ApiModelProperty(value = "创建时间")
    private String createdAt;

}
