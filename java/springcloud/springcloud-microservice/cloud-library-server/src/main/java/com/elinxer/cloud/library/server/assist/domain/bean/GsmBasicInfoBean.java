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
@ApiModel(value = "4g模块消息")
public class GsmBasicInfoBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "信号强度")
    private String  signalRssi;



}
