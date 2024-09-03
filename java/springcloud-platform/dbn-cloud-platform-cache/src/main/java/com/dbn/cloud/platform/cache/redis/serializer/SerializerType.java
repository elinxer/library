package com.dbn.cloud.platform.cache.redis.serializer;

/**
 * 序列化类型
 */
public enum SerializerType {
    /**
     * json 序列化
     */
    JACK_SON,
    /**
     * ProtoStuff 序列化
     */
    ProtoStuff,
    /**
     * jdk 序列化
     */
    JDK,
    /**
     * HESSIAN2 序列化
     */
    HESSIAN2


}

