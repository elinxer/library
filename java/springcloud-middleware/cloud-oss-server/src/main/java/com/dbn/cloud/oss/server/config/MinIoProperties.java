package com.dbn.cloud.oss.server.config;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Component("minIoProperties")
@ConfigurationProperties(prefix = "minio")
public class MinIoProperties {

    private String uri;
    private String endpoint;
    private String accessKey;
    private String secretKey;

}
