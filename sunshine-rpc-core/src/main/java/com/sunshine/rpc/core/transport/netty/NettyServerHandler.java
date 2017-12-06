package com.sunshine.rpc.core.transport.netty;

import com.sunshine.rpc.core.SunshineRpcRequest;
import com.sunshine.rpc.core.SunshineRpcResponse;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class NettyServerHandler  extends SimpleChannelInboundHandler<SunshineRpcRequest> {


    protected void messageReceived(ChannelHandlerContext channelHandlerContext, SunshineRpcRequest rpcRequest) throws Exception {
        SunshineRpcResponse response = new SunshineRpcResponse();
        response.setRequestId(rpcRequest.getRequestId());
        System.out.println("SERVER接收到消息: "+ rpcRequest);

        channelHandlerContext.channel().writeAndFlush(response);
    }
}
