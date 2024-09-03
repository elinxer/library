package com.dbn.cloud.oss.client.domain.vo.request;

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
@ApiModel(value = "FileReqVo", description = "文件")
public class FileReqVo {

    @ApiModelProperty(value = "文件夹名称")
    private String bucketName;

    @ApiModelProperty(value = "文件名称", required = true)
    private String fileName;

    @ApiModelProperty(value = "hash项：1 检查hash->MD5")
    private Integer hash;

}
