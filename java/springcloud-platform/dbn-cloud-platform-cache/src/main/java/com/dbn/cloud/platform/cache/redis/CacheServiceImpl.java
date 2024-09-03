package com.dbn.cloud.platform.cache.redis;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author elinx
 */
@Service
public class CacheServiceImpl implements CacheService, ICacheService {

    private long timeout = 1L;

    private TimeUnit timeUnit = TimeUnit.HOURS;

    @Autowired
    private StringRedisTemplate stringRedisTemplate;

    @Autowired
    public RedisTemplate<String, Object> objectRedisTemplate;

    @Override
    public void setCache(String key, String value, long timeout, TimeUnit timeUnit) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, timeUnit);
    }

    @Override
    public void setCache(String key, String value, long timeout) {
        stringRedisTemplate.opsForValue().set(key, value, timeout, TimeUnit.SECONDS);
    }

    @Override
    public void setCache(String key, String value) {
        stringRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setObjectCache(String key, Object value) {
        objectRedisTemplate.opsForValue().set(key, value);
    }

    @Override
    public void setObjectCache(String key, Object value, long timeout, TimeUnit unit) {
        objectRedisTemplate.opsForValue().set(key, value, timeout, unit);
    }

    @Override
    public void deleteObject(String key) {
        objectRedisTemplate.delete(key);
    }

    @Override
    public String getCache(String key) {
        return stringRedisTemplate.opsForValue().get(key);
    }

    @Override
    public Object getObjectCache(String key) {
        return objectRedisTemplate.opsForValue().get(key);
    }

    @Override
    public void deleteCache(String key) {
        stringRedisTemplate.delete(key);
    }

    @Override
    public long getExpireCache(String key) {
        return stringRedisTemplate.getExpire(key);
    }

    @Override
    public Boolean expireCache(String key, long timeout, TimeUnit timeUnit) {
        return stringRedisTemplate.expire(key, timeout, timeUnit);
    }

    @Override
    public Set<String> keysCache(String key) {
        return stringRedisTemplate.keys(key);
    }

    @Override
    public <V, K> String getCache(K key, Closure<V, K> closure) {
        return doGetCache(key, closure, this.timeout, this.timeUnit);
    }

    @Override
    public <V, K> String getCache(K key, Closure<V, K> closure, long timeout, TimeUnit timeUnit) {
        return doGetCache(key, closure, timeout, timeUnit);
    }

    @Override
    public void deleteBatchCache(String key) {
        Set<String> keys = stringRedisTemplate.keys(key + "*");
        if (keys != null) {
            stringRedisTemplate.delete(keys);
        }
    }

    @Override
    public StringRedisTemplate getSrt() {
        return this.stringRedisTemplate;
    }

    private <K, V> String doGetCache(K key, Closure<V, K> closure, long timeout, TimeUnit timeUnit) {
        String ret = getCache(key.toString());
        if (ret == null) {
            Object r = closure.execute(key);
            setCache(key.toString(), r.toString(), timeout, timeUnit);
            return r.toString();
        }
        return ret;
    }

}
