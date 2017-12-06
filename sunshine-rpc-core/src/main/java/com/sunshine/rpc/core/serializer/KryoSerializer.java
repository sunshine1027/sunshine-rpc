package com.sunshine.rpc.core.serializer;

import com.esotericsoftware.kryo.Kryo;
import com.esotericsoftware.kryo.io.Input;
import com.esotericsoftware.kryo.io.Output;
import com.esotericsoftware.kryo.serializers.BeanSerializer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * kryo对跨语言的支持差一点，但是速度比hessian快
 *
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class KryoSerializer extends AbstractSerializer {
    private final Logger log = LoggerFactory.getLogger(getClass());

    public <T> byte[] serialize(T obj) {
        Kryo kryo = new Kryo();
        kryo.register(obj.getClass(), new BeanSerializer<T>(kryo, obj.getClass()));

        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        Output output = new Output(stream);
        kryo.writeObject(output, obj);
        output.flush();
        output.close();
        byte[] bytes = stream.toByteArray();
        try {
            stream.flush();
            stream.close();
        } catch (IOException e) {
            log.error("kryo serialize error", e);
        }
        return bytes;
    }

    public <T> Object deserialize(byte[] bytes, Class<T> clazz) {
        Kryo kryo = new Kryo();
        kryo.register(clazz, new BeanSerializer<T>(kryo, clazz)); //bean
        ByteArrayInputStream stream = new ByteArrayInputStream(bytes);
        Input input;
        Object simple = null;
        try {
            input = new Input(stream);
            input.close();
            simple = kryo.readObject(input, clazz);  //bean
        } catch (Exception e) {
            log.error("kryo deserialize error", e);
        }
        return simple;
    }
}
