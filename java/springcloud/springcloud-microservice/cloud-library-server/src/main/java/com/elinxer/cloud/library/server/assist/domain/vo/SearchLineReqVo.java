package com.elinxer.cloud.library.server.assist.domain.vo;

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
@ApiModel(value = "SearchLineReqVo")
public class SearchLineReqVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "签名：设备秘钥：签名当前时间戳")
    @NotEmpty(message = "签名不能为空")
    private String sign;

    @ApiModelProperty(value = "距离")
    private Double distance;

    @ApiModelProperty(value = "当前点（经,纬）", example = "113.349652,23.005389")
    @NotNull(message = "当前点不能为空")
    private String location;

}
