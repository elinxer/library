package com.elinxer.cloud.library.server.assist.domain;

import com.elinxer.cloud.library.server.assist.domain.enums.DeviceStatusEnum;
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
@ApiModel(value = "DeviceStatus")
public class DeviceStatus implements Serializable {

    private static final long serialVersionUID = 1L;

    private String clientId;
    private String deviceId;
    private Long timestamp;
    private DeviceStatusEnum status;

}
