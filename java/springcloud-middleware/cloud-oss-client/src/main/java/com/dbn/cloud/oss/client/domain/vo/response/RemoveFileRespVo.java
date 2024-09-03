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
@ApiModel(value = "RemoveFileRespVo", description = "文件移除返回参数")
public class RemoveFileRespVo {

    @ApiModelProperty(value = "移除结果")
    private boolean result;
}
