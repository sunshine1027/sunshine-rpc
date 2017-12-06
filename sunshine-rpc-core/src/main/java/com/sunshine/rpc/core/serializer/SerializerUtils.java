package com.sunshine.rpc.core.serializer;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class SerializerUtils {
    public static AbstractSerializer getSerializer(SerializerEnum serializerEnum) {
        switch (serializerEnum) {
            case HESSIAN:
                return new HessianSerializer();
            case KRYO:
                return new KryoSerializer();
            default:
                return new KryoSerializer();
        }
    }

    public static AbstractSerializer getDefaultSerializer() {
        return new KryoSerializer();

    }
}
