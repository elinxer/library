package com.elinxer.cloud.library.server.assist.config;

import com.dbn.cloud.platform.exception.feign.FeignExceptionConfig;
import feign.codec.Encoder;
import feign.form.spring.SpringFormEncoder;
import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.openfeign.support.SpringEncoder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 让 feign支持含MultiPart的dto 解析
 * <p>
 * Created on 2020-03-25
 */
@Configuration
public class MultiPartSupportConfiguration extends FeignExceptionConfig {

    @Autowired
    private ObjectFactory<HttpMessageConverters> messageConverters;

    @Bean
    public Encoder feignFormEncoder() {
        return new SpringFormEncoder(new SpringEncoder(messageConverters));
    }

}
