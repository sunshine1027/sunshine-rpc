package com.sunshine.rpc.core.serializer;

import java.io.Serializable;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public abstract class AbstractSerializer {
    public abstract <T> byte[] serialize(T obj);
    public abstract <T> Object deserialize(byte[] bytes, Class<T> clazz);
}
