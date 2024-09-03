package com.dbn.cloud.platform.cache.redis;

import org.springframework.data.redis.core.StringRedisTemplate;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * @author elinx
 */
public interface CacheService {

    /**
     * 设置缓存
     *
     * @param key      缓存 KEY
     * @param value    缓存值
     * @param timeout  缓存过期时间
     * @param timeUnit 缓存过期时间单位
     */
    public void setCache(String key, String value, long timeout, TimeUnit timeUnit);

    /**
     * 设置缓存
     *
     * @param key     缓存 KEY
     * @param value   缓存值
     * @param timeout 缓存过期时间
     */
    public void setCache(String key, String value, long timeout);

    /**
     * 设置缓存 不过期
     *
     * @param key   缓存KEY
     * @param value 缓存值
     */
    public void setCache(String key, String value);

    /**
     * 设置对象缓存
     *
     * @param key   缓存KEY
     * @param value 缓存值
     */
    public void setObjectCache(String key, Object value);

    /**
     * 设置对象缓存
     *
     * @param key     缓存KEY
     * @param value   缓存值
     * @param timeout 过期时间
     * @param unit    时间单位
     */
    public void setObjectCache(String key, Object value, long timeout, TimeUnit unit);

    /**
     * 删除一个指定的Object
     *
     * @param key 键名称
     */
    public void deleteObject(String key);

    /**
     * 获取缓存
     *
     * @param key 缓存KEY
     * @return String
     */
    public String getCache(String key);

    /**
     * 获取对象缓存内容
     *
     * @param key 缓存KEY
     * @return Object
     */
    public Object getObjectCache(String key);

    /**
     * 获取key的存活时间
     *
     * @param key 缓存KEY
     * @return long
     */
    long getExpireCache(String key);

    /**
     * 设置超时时间
     *
     * @param key      缓存KEY
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return Boolean
     */
    Boolean expireCache(String key, long timeout, TimeUnit timeUnit);

    /**
     * 批量获取缓存值
     *
     * @param key 缓存KEY
     * @return Set<String>
     */
    Set<String> keysCache(String key);

    /**
     * 获取缓存
     *
     * @param key     缓存KEY
     * @param closure Closure<V, K>
     * @return <V, K> String
     */
    public <V, K> String getCache(K key, Closure<V, K> closure);

    /**
     * 获取缓存
     *
     * @param key      缓存KEY
     * @param closure  Closure<V, K>
     * @param timeout  过期时间
     * @param timeUnit 时间单位
     * @return <V, K> String
     */
    public <V, K> String getCache(K key, Closure<V, K> closure, long timeout, TimeUnit timeUnit);

    /**
     * 删除缓存
     *
     * @param key 缓存KEY
     */
    public void deleteCache(String key);

    /**
     * 批量删除缓存
     *
     * @param key 缓存KEY
     */
    public void deleteBatchCache(String key);

    /**
     * 获取缓存基础句柄
     *
     * @return StringRedisTemplate
     */
    StringRedisTemplate getSrt();

}
