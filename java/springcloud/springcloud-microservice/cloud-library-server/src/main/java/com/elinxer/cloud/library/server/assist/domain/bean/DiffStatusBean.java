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
@ApiModel(value = "差分源状态信息")
public class DiffStatusBean implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "电台场强")
    private Float radioRssi;

    @ApiModelProperty(value = "千寻sdk状态")
    private String statusQxwz;

    @ApiModelProperty(value = "六分科技sdk状态")
    private String statusSixents;

    @ApiModelProperty(value = "中国移动sdk状态")
    private String statusCmcc;

    @ApiModelProperty(value = "ntrip状态")
    private String statusNtrip;


}
