package com.dbn.cloud.platform.common.config;

import feign.Logger.Level;
import org.springframework.context.annotation.Bean;

/**
 * Feign全局配置
 *
 * @author elinx
 * @version 1.0
 */
public class GlobalFeignConfig {

    @Bean
    public Level level() {
        return Level.FULL;
    }
}
