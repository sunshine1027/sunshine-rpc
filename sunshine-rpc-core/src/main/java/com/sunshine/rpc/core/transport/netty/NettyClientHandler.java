package com.sunshine.rpc.core.transport.netty;

import com.sunshine.rpc.core.SunshineRpcResponse;
import com.sunshine.rpc.core.transport.RpcCallbackUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<SunshineRpcResponse> {

    protected void channelRead0(ChannelHandlerContext ctx, SunshineRpcResponse rpcResponse)
            throws Exception {
        //messageReceived方法,名称很别扭，像是一个内部方法.
        RpcCallbackUtils.callbacks.put(rpcResponse.getRequestId(), rpcResponse);

    }
}
