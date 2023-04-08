package com.elinxer.cloud.library.server.assist.domain;

import io.swagger.annotations.ApiModel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ApiModel(value = "DeviceStatusSummary")
public class DeviceStatusSummary implements Serializable {

    private static final long serialVersionUID = 1L;

    private Integer total;
    private Integer online;
    private Integer offline;

}
