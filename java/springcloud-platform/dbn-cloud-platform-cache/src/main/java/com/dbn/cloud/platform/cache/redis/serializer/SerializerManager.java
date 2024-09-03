package com.dbn.cloud.platform.cache.redis.serializer;


import com.dbn.cloud.platform.cache.redis.serializer.hessian.HessianSerializer;
import com.dbn.cloud.platform.cache.redis.serializer.jdk.JdkSerializer;

/**
 * Serializer管理器
 */
public class SerializerManager {

    private static Serializer[] serializers = new Serializer[5];

    public static final byte JDK = 0;
    public static final byte HESSIAN2 = 1;
    public static final byte JACK_SON = 2;


    static {
        addSerializer(JDK, new JdkSerializer());
        addSerializer(HESSIAN2, new HessianSerializer());
    }

    public static Serializer getSerializer(int idx) {
        return serializers[idx];
    }

    public static void addSerializer(int idx, Serializer serializer) {
        if (serializers.length <= idx) {
            Serializer[] newSerializers = new Serializer[idx + 5];
            System.arraycopy(serializers, 0, newSerializers, 0, serializers.length);
            serializers = newSerializers;
        }
        serializers[idx] = serializer;
    }
}
