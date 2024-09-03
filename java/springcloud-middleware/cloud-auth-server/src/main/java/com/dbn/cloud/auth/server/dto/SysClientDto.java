package com.dbn.cloud.auth.server.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "客户端DTO")
public class SysClientDto {

    @ApiModelProperty(value = "主键id")
    private Long id;

    @ApiModelProperty(value = "应用标识")
    private String clientId;

    @ApiModelProperty(value = "应用密钥")
    private String clientSecret;

    @ApiModelProperty(value = "应用密钥:明文")
    private String clientSecretStr;

    @ApiModelProperty(value = "授权方式:authorization_code,password,refresh_token,client_credentials")
    private String authorizedGrantTypes = "authorization_code,password,refresh_token,client_credentials";

    @ApiModelProperty(value = "access_token有效期")
    private Integer accessTokenValidity = 18000;

    @ApiModelProperty(value = "refresh_token有效期")
    private Integer refreshTokenValidity = 18000;

    @ApiModelProperty(value = "状态:true-有效，false-无效")
    private Boolean status;

}
