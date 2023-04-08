package com.elinxer.cloud.library.server.assist.domain.vo.user;

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
@ApiModel(value = "UserRegVo")
public class UserRegVo implements Serializable {

    private static final long serialVersionUID = 1L;


//    @ApiModelProperty(value = "账号")
//    private String username;
//
//    @ApiModelProperty(value = "密码")
//    private String password;

    @ApiModelProperty(value = "姓名")
    private String realName;

    @ApiModelProperty(value = "手机号")
    private String phone;

    @ApiModelProperty(value = "短信验证码")
    private String smsCode;

    @ApiModelProperty(value = "设备序列号")
    private String deviceNo;


}
