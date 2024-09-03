package com.dbn.cloud.platform.cache.redis.serializer.hessian;


import com.caucho.hessian.io.Hessian2Input;
import com.caucho.hessian.io.Hessian2Output;
import com.caucho.hessian.io.SerializerFactory;
import com.dbn.cloud.platform.cache.redis.serializer.Serializer;
import org.springframework.data.redis.serializer.SerializationException;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * Serializer for serialize and deserialize.
 *
 * @author elinx
 * @version 1.0.0
 */
public class HessianSerializer implements Serializer {

    private SerializerFactory serializerFactory = new SerializerFactory();


    @Override
    public byte[] serialize(Object obj) throws SerializationException {
        ByteArrayOutputStream byteArray = new ByteArrayOutputStream();
        Hessian2Output output = new Hessian2Output(byteArray);
        output.setSerializerFactory(serializerFactory);
        try {
            output.writeObject(obj);
        } catch (IOException e) {
            throw new SerializationException("IOException occurred when Hessian serializer encode!", e);
        } finally {
            try {
                output.close();
            } catch (IOException e) {
            }
        }
        return byteArray.toByteArray();
    }


    @SuppressWarnings("unchecked")
    @Override
    public <T> T deserialize(byte[] data) throws SerializationException {
        Hessian2Input input = new Hessian2Input(new ByteArrayInputStream(data));
        input.setSerializerFactory(serializerFactory);
        Object resultObject = null;
        try {
            resultObject = input.readObject();
        } catch (IOException e) {
            throw new SerializationException("IOException occurred when Hessian serializer decode!", e);
        } finally {
            try {
                input.close();
            } catch (IOException e) {
            }
        }
        return (T) resultObject;
    }

}
