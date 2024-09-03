package com.dbn.cloud.platform.sms.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Component
@ConfigurationProperties(prefix = "sms.aliyun")
public class SmsConfigProperties {

    private String regionId;
    private String accessKeyId;
    private String secret;
    private String signName;
    private String templateCode;

}
