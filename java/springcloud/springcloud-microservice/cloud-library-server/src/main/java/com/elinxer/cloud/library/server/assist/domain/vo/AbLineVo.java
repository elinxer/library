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
@ApiModel(value = "abline管理")
public class AbLineVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "签名：设备秘钥：签名当前时间戳")
    @NotEmpty(message = "签名不能为空")
    private String sign;

    @ApiModelProperty(value = "clientId")
    @NotNull(message = "clientId不能为空")
    private String clientId;

    @ApiModelProperty(value = "当前时间戳：13位，毫秒")
    @NotNull(message = "当前时间戳不能为空")
    private Long timestamp;

    @ApiModelProperty(value = "名称")
    @NotEmpty(message = "名称不能为空")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "终端编号")
    @NotEmpty(message = "终端编号不能为空")
    private String deviceCode;

    @ApiModelProperty(value = "ab线参数")
    private String params;

    @ApiModelProperty(value = "ab线点集合")
    @NotEmpty(message = "ab线点集合不能为空")
    private String point;

    @ApiModelProperty(value = "幅宽")
    @NotNull(message = "幅宽不能为空")
    private Double width;

    @ApiModelProperty(value = "geo hash")
    private String geoHash;

    @ApiModelProperty(value = "当前点（经,纬）", example = "113.349652,23.005389")
    @NotNull(message = "当前点不能为空")
    private String location;

}
