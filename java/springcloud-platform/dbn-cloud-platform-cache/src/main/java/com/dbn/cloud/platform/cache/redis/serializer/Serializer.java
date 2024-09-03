package com.dbn.cloud.platform.cache.redis.serializer;

import org.springframework.data.redis.serializer.SerializationException;

/**
 * Serializer for serialize and deserialize.
 */
public interface Serializer {
    /**
     * Encode object into bytes.
     *
     * @param obj target object
     * @return serialized result
     */
    byte[] serialize(final Object obj) throws SerializationException;

    /**
     * Decode bytes into Object.
     *
     * @param data serialized data
     */
    <T> T deserialize(final byte[] data) throws SerializationException;
}
