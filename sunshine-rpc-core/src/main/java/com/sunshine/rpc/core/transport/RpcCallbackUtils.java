package com.sunshine.rpc.core.transport;

import com.sunshine.rpc.core.SunshineRpcResponse;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class RpcCallbackUtils {
    private static ReentrantLock lock = new ReentrantLock(false);
    public static ConcurrentHashMap<String, SunshineRpcResponse> callbacks = new ConcurrentHashMap<String, SunshineRpcResponse>();

    public static SunshineRpcResponse getResult(String requestId, long millisecond) throws InterruptedException {
        Condition condition = lock.newCondition();
        SunshineRpcResponse response;
        for (int i = 0; i < 1000; i++) {
            response = callbacks.get(requestId);
            if (response != null) {
                return response;
            }
            condition.await(millisecond, TimeUnit.MICROSECONDS);
        }
        return null;
    }
}
