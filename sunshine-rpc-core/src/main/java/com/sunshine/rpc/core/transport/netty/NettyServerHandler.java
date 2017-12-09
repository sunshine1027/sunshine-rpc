package com.sunshine.rpc.core.transport.netty;

import com.sunshine.rpc.core.SunshineRpcRequest;
import com.sunshine.rpc.core.SunshineRpcResponse;
import com.sunshine.rpc.core.util.BeanUtils;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.springframework.cglib.reflect.FastClass;
import org.springframework.cglib.reflect.FastMethod;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class NettyServerHandler extends SimpleChannelInboundHandler<SunshineRpcRequest> {


    protected void messageReceived(ChannelHandlerContext channelHandlerContext, SunshineRpcRequest rpcRequest) throws Exception {
        SunshineRpcResponse response = new SunshineRpcResponse();
        response.setRequestId(rpcRequest.getRequestId());
        System.out.println("SERVER接收到消息: "+ rpcRequest);

        try {
            String className = rpcRequest.getClassName();
            Class<?> iface = Class.forName(className);
            String methodName = rpcRequest.getMethodName();
            Class<?>[] parameterTypes = rpcRequest.getParameterTypes();
            Object[] parameters = rpcRequest.getParameters();

            /*Method method = serviceClass.getMethod(methodName, parameterTypes);
            method.setAccessible(true);
            return method.invoke(serviceBean, parameters);*/

            FastClass serviceFastClass = FastClass.create(iface);
            FastMethod serviceFastMethod = serviceFastClass.getMethod(methodName, parameterTypes);

            Object result = serviceFastMethod.invoke(BeanUtils.getBean(iface), parameters);

            response.setResult(result);
        } catch (Throwable t) {
            t.printStackTrace();
            response.setError(t);
        }
        channelHandlerContext.channel().writeAndFlush(response);
    }
}
