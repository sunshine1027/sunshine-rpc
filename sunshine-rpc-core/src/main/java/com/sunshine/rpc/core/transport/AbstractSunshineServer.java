package com.sunshine.rpc.core.transport;

import com.sunshine.rpc.core.serializer.AbstractSerializer;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public abstract class AbstractSunshineServer {
    private int port;
    private AbstractSerializer serializer;

    public abstract void startServer();
    private AbstractSunshineServer() {
    }

    public AbstractSunshineServer(int port, AbstractSerializer serializer) {
        this.port = port;
        this.serializer = serializer;
    }

    public int getPort() {
        return port;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public AbstractSerializer getSerializer() {
        return serializer;
    }

    public void setSerializer(AbstractSerializer serializer) {
        this.serializer = serializer;
    }


}
