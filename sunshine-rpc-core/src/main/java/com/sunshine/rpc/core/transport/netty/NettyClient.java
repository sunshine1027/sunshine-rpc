package com.sunshine.rpc.core.transport.netty;

import com.sunshine.rpc.core.SunshineRpcRequest;
import com.sunshine.rpc.core.SunshineRpcResponse;
import com.sunshine.rpc.core.transport.AbstractSunshineClient;
import com.sunshine.rpc.core.transport.RpcCallbackUtils;
import com.sunshine.rpc.core.zookeeper.ZookeeperDiscovery;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.util.concurrent.ConcurrentHashMap;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class NettyClient extends AbstractSunshineClient {
    private static ConcurrentHashMap<String, Channel> channelMap = new ConcurrentHashMap<String, Channel>();
    public SunshineRpcResponse send(SunshineRpcRequest request) throws Exception {
        String address = ZookeeperDiscovery.discover(request.getServerGroupId(), request.getMethodName());
        String host = address.split(":")[0];
        int port = Integer.parseInt(address.split(":")[1]);
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
    public Bootstrap getBootstrap(){
        EventLoopGroup group = new NioEventLoopGroup();
        Bootstrap b = new Bootstrap();
        b.group(group).channel(NioSocketChannel.class);
        b.handler(new ChannelInitializer<Channel>() {
            @Override
            protected void initChannel(Channel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new NettyDecoder(SunshineRpcResponse.class, serializer));
                pipeline.addLast(new NettyEncoder(SunshineRpcRequest.class, serializer));
                pipeline.addLast(new NettyClientHandler());
            }
        });
        b.option(ChannelOption.SO_KEEPALIVE, true);
        return b;
    }

    public Channel getChannel(String host, int port){
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
