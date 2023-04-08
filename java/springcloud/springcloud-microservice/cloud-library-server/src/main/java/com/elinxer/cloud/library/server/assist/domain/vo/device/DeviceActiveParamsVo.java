package com.elinxer.cloud.library.server.assist.domain.vo.device;

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
@ApiModel(value = "DeviceActiveParamsVo")
public class DeviceActiveParamsVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备编号")
    @NotEmpty(message = "deviceNo必填")
    private String deviceNo;

    @ApiModelProperty(value = "用户ID")
    @NotEmpty(message = "userId必填")
    private String userId;

    @ApiModelProperty(value = "当前时间")
    @NotEmpty(message = "timestamp必填")
    private String timestamp;

}
