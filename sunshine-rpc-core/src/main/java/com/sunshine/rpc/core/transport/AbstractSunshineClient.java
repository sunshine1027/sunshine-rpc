package com.sunshine.rpc.core.transport;

import com.sunshine.rpc.core.SunshineRpcRequest;
import com.sunshine.rpc.core.SunshineRpcResponse;
import com.sunshine.rpc.core.serializer.AbstractSerializer;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public abstract class AbstractSunshineClient {
    protected AbstractSerializer serializer;
    protected long timeoutMillis;

    public void init(AbstractSerializer serializer, long timeoutMillis) {
        this.serializer = serializer;
        this.timeoutMillis = timeoutMillis;
    }

    // 一期先只做同步的
    public abstract SunshineRpcResponse send(SunshineRpcRequest request) throws Exception;
}
