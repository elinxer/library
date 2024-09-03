package com.dbn.cloud.auth.server.dto;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

@Data
public class LoginUserDto {

    @ApiModelProperty(value = "账号", required = true)
    private String username;

    @ApiModelProperty(value = "密码", required = true)
    private String password;



}
