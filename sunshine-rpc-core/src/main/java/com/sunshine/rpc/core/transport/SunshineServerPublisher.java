package com.sunshine.rpc.core.transport;

import com.sunshine.rpc.core.serializer.AbstractSerializer;
import com.sunshine.rpc.core.serializer.SerializerUtils;
import com.sunshine.rpc.core.transport.netty.NettyServer;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class SunshineServerPublisher implements InitializingBean {
    private String groupId;
    private int port;
    private AbstractSerializer serializer = SerializerUtils.getDefaultSerializer();
    private Class<?> serviceIface;//接口全名
    private String serviceImpl;//实现的bean

    public SunshineServerPublisher() {
    }

    public SunshineServerPublisher(String groupId, int port, AbstractSerializer serializer, Class<?> serviceIface, String serviceImpl) {
        this.groupId = groupId;
        this.port = port;
        this.serializer = serializer;
        this.serviceIface = serviceIface;
        this.serviceImpl = serviceImpl;
    }

    public String getGroupId() {
        return groupId;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
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

    public Class<?> getServiceIface() {
        return serviceIface;
    }

    public void setServiceIface(Class<?> serviceIface) {
        this.serviceIface = serviceIface;
    }

    public String getServiceImpl() {
        return serviceImpl;
    }

    public void setServiceImpl(String serviceImpl) {
        this.serviceImpl = serviceImpl;
    }

    public void afterPropertiesSet() throws Exception {
        AbstractSunshineServer nettyServer = new NettyServer(port, serializer);
        nettyServer.startServer();
    }
}
