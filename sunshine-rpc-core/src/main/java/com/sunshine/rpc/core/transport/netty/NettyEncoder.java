package com.sunshine.rpc.core.transport.netty;

import com.sunshine.rpc.core.serializer.AbstractSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class NettyEncoder extends MessageToByteEncoder<Object> {

    private Class<?> genericClass;
    private AbstractSerializer serializer;

    public NettyEncoder(Class<?> genericClass, AbstractSerializer serializer) {
        super();
        this.genericClass = genericClass;
        this.serializer = serializer;
    }

    @Override
    public void encode(ChannelHandlerContext ctx, Object in, ByteBuf out) throws Exception {
        if (genericClass.isInstance(in)) {
            byte[] data = serializer.serialize(in);
            out.writeInt(data.length);
            out.writeBytes(data);
        }
    }
}