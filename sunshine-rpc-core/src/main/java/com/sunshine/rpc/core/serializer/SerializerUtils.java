package com.sunshine.rpc.core.serializer;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class SerializerUtils {
    public static AbstractSerializer getSerializer(SerializerEnum serializerEnum) {
        switch (serializerEnum) {
            case MESSAGE_PACK:
                return new MessagePackSerializer();
            case PROTOBUF:
                return new ProtobufSerializer();
            default:
                return new MessagePackSerializer();
        }
    }

    public static AbstractSerializer getDefaultSerializer() {
        return new MessagePackSerializer();

    }
}
