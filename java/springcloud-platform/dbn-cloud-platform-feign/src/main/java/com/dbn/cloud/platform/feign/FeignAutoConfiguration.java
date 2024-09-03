package com.dbn.cloud.platform.feign;

import com.alibaba.csp.sentinel.adapter.spring.webmvc.callback.RequestOriginParser;
import com.dbn.cloud.platform.feign.common.SentinelFeign;
import com.dbn.cloud.platform.feign.parser.HeaderRequestOriginParser;
import feign.Feign;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

public class FeignAutoConfiguration {

    @Bean
    @Scope("prototype")
    @ConditionalOnMissingBean
    @ConditionalOnProperty(name = "feign.sentinel.enabled")
    public Feign.Builder feignSentinelBuilder() {
        return SentinelFeign.builder();
    }


    @Bean
    @ConditionalOnMissingBean
    public RequestOriginParser requestOriginParser() {
        return new HeaderRequestOriginParser();
    }

}
