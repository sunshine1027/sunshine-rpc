package com.sunshine.rpc.core.transport.netty;

import com.sunshine.rpc.core.SunshineRpcRequest;
import com.sunshine.rpc.core.SunshineRpcResponse;
import com.sunshine.rpc.core.serializer.AbstractSerializer;
import com.sunshine.rpc.core.transport.AbstractSunshineServer;
import com.sunshine.rpc.core.util.IpUtils;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioServerSocketChannel;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class NettyServer extends AbstractSunshineServer {
    public NettyServer(int port, AbstractSerializer serializer) {
        super(port, serializer);
    }

    public void startServer() {
        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap b = new ServerBootstrap();
        b.group(bossGroup, workerGroup);
        b.channel(NioServerSocketChannel.class);
        b.childHandler(new ChannelInitializer<SocketChannel>() {
            @Override
            public void initChannel(SocketChannel ch) throws Exception {
                ChannelPipeline pipeline = ch.pipeline();
                pipeline.addLast(new NettyDecoder(SunshineRpcResponse.class, getSerializer()));
                pipeline.addLast(new NettyEncoder(SunshineRpcRequest.class, getSerializer()));
                pipeline.addLast(new NettyServerHandler());
            }
        });

        try {
            b.bind(IpUtils.getIp(), getPort()).sync();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
