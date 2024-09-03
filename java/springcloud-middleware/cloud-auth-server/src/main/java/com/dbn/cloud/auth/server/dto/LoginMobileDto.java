package com.dbn.cloud.auth.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginMobileDto {

    @ApiModelProperty(value = "mobile phone", required = true)
    private String mobile;

    @ApiModelProperty(value = "smsCode", required = true)
    private String smsCode;



}
