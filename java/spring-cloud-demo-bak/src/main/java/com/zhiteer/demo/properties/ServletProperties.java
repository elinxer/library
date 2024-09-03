package com.zhiteer.demo.properties;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;


@Data
@Configuration
@ConfigurationProperties(prefix = "spring.servlet")
public class ServletProperties {

    /**
     * 这是一个测试配置
     */
    private String context_path;

}
