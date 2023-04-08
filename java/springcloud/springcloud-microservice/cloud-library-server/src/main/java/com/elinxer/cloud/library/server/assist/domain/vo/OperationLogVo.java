package com.elinxer.cloud.library.server.assist.domain.vo;

import com.dbn.cloud.platform.validation.validator.group.UpdateGroup;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "作业工况")
public class OperationLogVo implements Serializable {

    private static final long serialVersionUID = 1L;

    @NotNull(groups = {UpdateGroup.class})
    @ApiModelProperty(value = "主键id，修改时必填，新增时不需要填", required = false)
    @Max(value = Long.MAX_VALUE, message = "id不能大于9223372036854775807")
    @Min(value = 0, message = "id不能小于0")
    @JsonSerialize(using = ToStringSerializer.class)
    private Long id;

    @ApiModelProperty(value = "作业编号")
    private String operationCode;

    @ApiModelProperty(value = "作业类型名称")
    private String typeName;

    @ApiModelProperty(value = "创建时间")
    private String createTime;

    @ApiModelProperty(value = "工况数据")
    private String json_value;

}
