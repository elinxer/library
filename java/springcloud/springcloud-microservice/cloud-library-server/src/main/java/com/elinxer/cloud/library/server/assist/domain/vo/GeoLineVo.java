package com.elinxer.cloud.library.server.assist.domain.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;
import java.util.Date;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "GeoLineVo")
public class GeoLineVo implements Serializable {

    private static final long serialVersionUID = 1L;


    @ApiModelProperty(value = "名称")
    private String name;

    @ApiModelProperty(value = "编码")
    private String code;

    @ApiModelProperty(value = "描述")
    private String desc;

    @ApiModelProperty(value = "ab线参数")
    private String params;

    @ApiModelProperty(value = "ab线点集合")
    private String point;

    @ApiModelProperty(value = "距离")
    private Double distance;

    @ApiModelProperty(value = "幅宽")
    private Double width;

    @ApiModelProperty(value = "创建时间")
    private Date createTime;

}
