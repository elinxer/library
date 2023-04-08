package com.elinxer.cloud.library.server.assist.domain.vo.user;

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
@ApiModel(value = "UserBindDeviceVo")
public class UserBindDeviceVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "设备序列号")
    @NotEmpty(message = "序列号不能为空！")
    private String deviceNo;

}
