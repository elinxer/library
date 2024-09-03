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
@ApiModel(value = "GetBatchFileUrlRespVo", description = "文件url")
public class GetBatchFileUrlRespVo {

    @ApiModelProperty(value = "文件url")
    private String fileUrl;

    @ApiModelProperty(value = "文件夹名称", required = true)
    private String bucketName;

    @ApiModelProperty(value = "文件名称", required = true)
    private String fileName;
}
