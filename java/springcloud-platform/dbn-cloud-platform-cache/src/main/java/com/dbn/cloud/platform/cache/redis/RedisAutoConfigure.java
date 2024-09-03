package com.dbn.cloud.platform.cache.redis;


import com.dbn.cloud.platform.cache.redis.config.DefaultRedisProperties;
import com.dbn.cloud.platform.cache.redis.lock.DistributedLock;
import com.dbn.cloud.platform.cache.redis.lock.RedisDistributedLock;
import com.dbn.cloud.platform.cache.redis.serializer.JsonSerializer;
import com.dbn.cloud.platform.cache.redis.serializer.SerializerType;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.JdkSerializationRedisSerializer;
import org.springframework.data.redis.serializer.RedisSerializer;
import org.springframework.data.redis.serializer.StringRedisSerializer;

@Slf4j
@ConditionalOnClass(RedisConnectionFactory.class)
//@ConditionalOnProperty(prefix = DbnRedisProperties.PREFIX, name = "enable", havingValue = "true")
@AutoConfigureBefore(RedisAutoConfiguration.class)
@EnableConfigurationProperties({RedisProperties.class, DefaultRedisProperties.class})
@RequiredArgsConstructor
public class RedisAutoConfigure {

    private final DefaultRedisProperties defaultRedisProperties;

    /**
     * 分布式锁
     */
    @Bean
    @ConditionalOnMissingBean
    public DistributedLock redisDistributedLock(@Qualifier("redisTemplate") RedisTemplate<String, Object> redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }

    /**
     * RedisTemplate配置
     */
    @Bean("redisTemplate")
    @ConditionalOnMissingBean
    public RedisTemplate<String, Object> redisTemplate(RedisConnectionFactory factory, RedisSerializer<Object> redisSerializer) {
        RedisTemplate<String, Object> template = new RedisTemplate<>();
        setSerializer(factory, template, redisSerializer);
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(RedisSerializer.class)
    public RedisSerializer<Object> redisSerializer() {
        SerializerType serializerType = defaultRedisProperties.getSerializerType();
        if (SerializerType.JDK == serializerType) {
            ClassLoader classLoader = this.getClass().getClassLoader();
            return new JdkSerializationRedisSerializer(classLoader);
        }
        return new JsonSerializer();
    }

    private void setSerializer(RedisConnectionFactory factory, RedisTemplate template, RedisSerializer<Object> redisSerializer) {
        RedisSerializer stringSerializer = new StringRedisSerializer();
        SerializerType serializerType = defaultRedisProperties.getSerializerType();
        if (SerializerType.JDK == serializerType) {
            JdkSerializationRedisSerializer jdkSeri = new JdkSerializationRedisSerializer(this.getClass().getClassLoader());
            template.setKeySerializer(stringSerializer);
            template.setHashKeySerializer(jdkSeri);
            template.setHashValueSerializer(jdkSeri);
            template.setValueSerializer(jdkSeri);
        } else {
            template.setKeySerializer(stringSerializer);
            template.setHashKeySerializer(stringSerializer);
            template.setHashValueSerializer(redisSerializer);
            template.setValueSerializer(redisSerializer);
        }
        template.setConnectionFactory(factory);
    }


    @Bean
    @ConditionalOnMissingBean(CacheService.class)
    public CacheService cacheService() {
        return new CacheServiceImpl();
    }
}


