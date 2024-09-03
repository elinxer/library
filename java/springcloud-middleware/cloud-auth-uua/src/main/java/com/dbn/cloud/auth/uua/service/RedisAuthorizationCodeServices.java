package com.dbn.cloud.auth.uua.service;

import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.code.RandomValueAuthorizationCodeServices;

import java.util.concurrent.TimeUnit;


public class RedisAuthorizationCodeServices extends RandomValueAuthorizationCodeServices {

    private RedisTemplate redisTemplate;


    public RedisTemplate getRedisTemplate() {
        return redisTemplate;
    }

    public void setRedisTemplate(RedisTemplate redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    /**
     * 替换JdbcAuthorizationCodeServices的存储策略
     * 将存储code到redis，并设置过期时间，10分钟<br>
     */
    @Override
    protected void store(String code, OAuth2Authentication authentication) {

        redisTemplate.opsForValue().set(redisKey(code), authentication, 10, TimeUnit.MINUTES);


    }

    @Override
    protected OAuth2Authentication remove(final String code) {

        String codeKey = redisKey(code);

        OAuth2Authentication token = (OAuth2Authentication) redisTemplate.opsForValue().get(codeKey);

        this.redisTemplate.delete(codeKey);

        return token;
    }

    /**
     * redis中 code key的前缀
     *
     * @param code
     * @return
     */
    private String redisKey(String code) {
        return "oauth:code:" + code;
    }
}
