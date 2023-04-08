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
@ApiModel(value = "DeviceActiveParamsRespVo")
public class DeviceActiveParamsRespVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备参数", required = true)
    private Object device;

    @ApiModelProperty(value = "设备配置")
    private Object config;

}
