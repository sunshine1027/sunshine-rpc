package com.sunshine.rpc.core.util;

import com.sunshine.rpc.core.transport.AbstractSunshineClient;
import com.sunshine.rpc.core.transport.TransportEnum;
import com.sunshine.rpc.core.transport.netty.NettyClient;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class TransportUtils {
    public static AbstractSunshineClient getClient(TransportEnum transportEnum) {
        switch (transportEnum) {
            case NETTY:
                return new NettyClient();
            default:
                //这里要新加，二期做
                return new NettyClient();
        }
    }
}
