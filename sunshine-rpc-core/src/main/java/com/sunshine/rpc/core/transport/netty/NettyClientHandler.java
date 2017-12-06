package com.sunshine.rpc.core.transport.netty;

import com.sunshine.rpc.core.SunshineRpcResponse;
import com.sunshine.rpc.core.transport.RpcCallbackUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<SunshineRpcResponse> {

    protected void messageReceived(ChannelHandlerContext channelHandlerContext, SunshineRpcResponse rpcResponse) throws Exception {
        RpcCallbackUtils.callbacks.put(rpcResponse.getRequestId(), rpcResponse);
    }
}
