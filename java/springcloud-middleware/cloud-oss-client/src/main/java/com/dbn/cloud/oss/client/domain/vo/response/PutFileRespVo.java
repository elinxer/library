package com.dbn.cloud.oss.client.domain.vo.response;

import com.fasterxml.jackson.annotation.JsonInclude;
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
@JsonInclude(JsonInclude.Include.NON_NULL)
@ApiModel(value = "PutFileRespVo", description = "文件上传返回参数")
public class PutFileRespVo {

    @ApiModelProperty(value = "旧文件名称")
    private String oldFileName;

    @ApiModelProperty(value = "新文件名称")
    private String newFileName;

    @ApiModelProperty(value = "文件url")
    private String fileUrl;

    @ApiModelProperty(value = "压缩文件url")
    private String compressFileUrl;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "文件hash")
    private String hash;

    @ApiModelProperty(value = "完整路径地址")
    private String outside;

}
