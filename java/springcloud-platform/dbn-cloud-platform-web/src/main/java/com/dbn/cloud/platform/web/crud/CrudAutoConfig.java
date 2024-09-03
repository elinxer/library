package com.dbn.cloud.platform.web.crud;

import com.dbn.cloud.platform.web.crud.exception.RestExceptionTranslator;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/*
 * Crud通用包的自动装配类
 *
 * @author elinx
 * @date 2021-09-02
 */
@Configuration
public class CrudAutoConfig {
    @Bean
    public RestExceptionTranslator restExceptionTranslator() {
        return new RestExceptionTranslator();
    }

}
