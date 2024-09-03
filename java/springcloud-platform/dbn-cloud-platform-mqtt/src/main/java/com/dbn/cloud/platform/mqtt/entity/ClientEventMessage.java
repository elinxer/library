package com.dbn.cloud.platform.mqtt.entity;

import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor
@Data
public class ClientEventMessage {
    private String username;
    private Long ts;
    private Integer sockport;
    private Integer protoVer;
    private String protoName;
    private Integer keepalive;
    private String ipaddress;
    private Integer expiryInterval;
    private Long connectedAt;
    private Integer connack;
    private String clientid;
    private Boolean cleanStart;
    private String reason;
    private Integer protoVerX;
    private String protoNameX;
    private Long disconnectedAt;
}
