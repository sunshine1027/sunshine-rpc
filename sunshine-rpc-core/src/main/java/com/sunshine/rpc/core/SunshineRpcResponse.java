package com.sunshine.rpc.core;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */

public class SunshineRpcResponse {
    private String requestId;
    private Throwable error;
    private Object result;

    public boolean isError() {
        return error != null;
    }

    public String getRequestId() {
        return requestId;
    }

    public void setRequestId(String requestId) {
        this.requestId = requestId;
    }

    public Throwable getError() {
        return error;
    }

    public void setError(Throwable error) {
        this.error = error;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    @Override
    public String toString() {
        return "SunshineRpcResponse{" +
                "requestId='" + requestId + '\'' +
                ", error=" + error +
                ", result=" + result +
                '}';
    }
}
