package com.dbn.cloud.platform.mqtt.config;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@ConfigurationProperties(prefix = "mqtt")
public class MqttClientProperties {
    private String clientId;
    private Integer connectTimeout;
    private Integer keepAliveInterval;
    private String endpoint;
    private Integer port;
    private Boolean isSSL;
    private String rootCaPath;
    private String certPath;
    private String keyPath;
    private Boolean cleanSession;
    private String userName;
    private String passWord;
    private List<String> initTopics;
}