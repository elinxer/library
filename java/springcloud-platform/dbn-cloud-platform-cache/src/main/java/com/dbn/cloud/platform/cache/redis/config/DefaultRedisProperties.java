package com.dbn.cloud.platform.cache.redis.config;

import com.dbn.cloud.platform.cache.redis.serializer.SerializerType;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Redis自定义配置类
 *
 * @author elinx
 */
@Data
@ConfigurationProperties(prefix = DefaultRedisProperties.PREFIX)
public class DefaultRedisProperties {

    public static final String PREFIX = "cloud.redis";

    public boolean enable = true;

    private SerializerType serializerType = SerializerType.JACK_SON;


}




