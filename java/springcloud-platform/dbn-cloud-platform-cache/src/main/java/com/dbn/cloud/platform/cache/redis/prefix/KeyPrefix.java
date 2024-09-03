package com.dbn.cloud.platform.cache.redis.prefix;

/**
 * KeyPrefix
 *
 * @author elinx
 * @version 1.0.0
 */
public interface KeyPrefix {

    public int expireSeconds();

    public String getPrefix();

}
