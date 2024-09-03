package com.dbn.cloud.platform.cache.redis;

public interface Closure<O, I> {
    public O execute(I input);
}