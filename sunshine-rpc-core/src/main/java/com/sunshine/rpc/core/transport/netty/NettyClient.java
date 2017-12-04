package com.sunshine.rpc.core.transport.netty;

import com.sunshine.rpc.core.SunshineRpcRequest;
import com.sunshine.rpc.core.SunshineRpcResponse;
import com.sunshine.rpc.core.transport.AbstractSunshineClient;
import com.sunshine.rpc.core.transport.RpcCallbackUtils;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.LengthFieldBasedFrameDecoder;
import io.netty.handler.codec.LengthFieldPrepender;
import io.netty.handler.codec.string.StringDecoder;
import io.netty.handler.codec.string.StringEncoder;
import io.netty.util.CharsetUtil;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class NettyClient extends AbstractSunshineClient {
    private ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<String, Channel>();
    public SunshineRpcResponse send(SunshineRpcRequest request) throws Exception {
        //todo 通过zk发现
        String host = "";
        int port = 0;
        Channel channel = getChannelFromCache(host, port);
        if (channel == null)
            return null;
        channel.writeAndFlush(request);
        return RpcCallbackUtils.getResult(request.getRequestId(), timeoutMillis);
    }

    private Channel getChannelFromCache(String host, int port) {
        if (channelMap.get(host + ":" + port) == null) {
            Channel channel = getChannel(host, port);
            if (channel == null) {
                System.out.println("channel is null......");
                return null;
            }
            channelMap.put(host + ":" + port, channel);
            return channel;
        }
        return channelMap.get(host + ":" + port);
    }

    /**
     * 初始化Bootstrap
     * @return
     */
    public static final Bootstrap getBootstrap(){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast("frameDecoder", new LengthFieldBasedFrameDecoder(Integer.MAX_VALUE, 0, 4, 0, 4));
                pipeline.addLast("frameEncoder", new LengthFieldPrepender(4));
                pipeline.addLast("decoder", new StringDecoder(CharsetUtil.UTF_8));
                pipeline.addLast("encoder", new StringEncoder(CharsetUtil.UTF_8));
                pipeline.addLast("handler", new NettyClientHandler());
            }
        });
        b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    public static Channel getChannel(String host, int port){
        Channel channel;
        try {
            channel = getBootstrap().connect(host, port).sync().channel();
        } catch (Exception e) {
            System.out.println(String.format("连接Server(IP[%s],PORT[%s])失败", host, port));
            return null;
        }
        return channel;
    }
}
