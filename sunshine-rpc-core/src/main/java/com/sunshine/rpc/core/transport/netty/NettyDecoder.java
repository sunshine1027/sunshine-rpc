package com.sunshine.rpc.core.transport.netty;

import com.sunshine.rpc.core.serializer.AbstractSerializer;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class NettyDecoder extends ByteToMessageDecoder {

    private Class<?> genericClass;
    private AbstractSerializer serializer;

    public NettyDecoder(Class<?> genericClass, AbstractSerializer serializer) {
        this.genericClass = genericClass;
        this.serializer = serializer;
    }

    @Override
    public final void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        if (in.readableBytes() < 4) {
            return;
        }
        in.markReaderIndex();
        int dataLength = in.readInt();
        if (dataLength < 0) {
            ctx.close();
        }
        if (in.readableBytes() < dataLength) {
            in.resetReaderIndex();
            return;
        }
        byte[] data = new byte[dataLength];
        in.readBytes(data);

        Object obj = serializer.deserialize(data, genericClass);
        out.add(obj);
    }
}

