package com.dbn.cloud.platform.security.serializer;


import com.dbn.cloud.platform.cache.redis.serializer.SerializerManager;
import org.springframework.security.oauth2.provider.token.store.redis.StandardStringSerializationStrategy;

public class DefaultSerializationStrategy extends StandardStringSerializationStrategy {


    @Override
    @SuppressWarnings("unchecked")
    protected <T> T deserializeInternal(byte[] bytes, Class<T> clazz) {
        return (T) SerializerManager.getSerializer(SerializerManager.HESSIAN2).deserialize(bytes);
    }

    @Override
    protected byte[] serializeInternal(Object object) {
        return SerializerManager.getSerializer(SerializerManager.HESSIAN2).serialize(object);
    }
}