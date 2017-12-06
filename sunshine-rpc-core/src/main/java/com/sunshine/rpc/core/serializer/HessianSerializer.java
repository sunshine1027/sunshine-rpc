package com.sunshine.rpc.core.serializer;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class HessianSerializer extends AbstractSerializer{
    public <T> byte[] serialize(T obj) {
        return new byte[0];
    }

    public <T> Object deserialize(byte[] bytes, Class<T> clazz) {
        return null;
    }
}
