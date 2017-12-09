package com.sunshine.rpc.core.transport;

import com.sunshine.rpc.core.SunshineRpcRequest;
import com.sunshine.rpc.core.SunshineRpcResponse;
import com.sunshine.rpc.core.serializer.AbstractSerializer;
import com.sunshine.rpc.core.serializer.SerializerUtils;
import com.sunshine.rpc.core.util.TransportUtils;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.beans.factory.InitializingBean;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.UUID;

/**
 * rpc接口调用代理类
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class SunshineClientProxy implements FactoryBean<Object>, InitializingBean {
    private String groupId;
    private String remoteGroupId;
    private TransportEnum transportEnum = TransportEnum.NETTY;
    private AbstractSerializer serializer = SerializerUtils.getDefaultSerializer();
    private Class<?> serviceIface;

    private AbstractSunshineClient client = null;
    public SunshineClientProxy() {
    }



    public Object getObject() throws Exception {
        return Proxy.newProxyInstance(Thread.currentThread()
                        .getContextClassLoader(), new Class[] {serviceIface},
                new InvocationHandler() {
                    public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {

                        // request
                        SunshineRpcRequest request = new SunshineRpcRequest();
                        request.setRequestId(UUID.randomUUID().toString());
                        request.setCreateMillisTime(System.currentTimeMillis());
                        request.setClassName(method.getDeclaringClass().getName());
                        request.setMethodName(method.getName());
                        request.setParameterTypes(method.getParameterTypes());
                        request.setParameters(args);

                        // send
                        SunshineRpcResponse response = client.send(request);
                        // valid response
                        if (response == null) {
                            throw new Exception("netty response not found.");
                        }
                        if (response.isError()) {
                            throw response.getError();
                        } else {
                            return response.getResult();
                        }

                    }
                });
    }

    public Class<?> getObjectType() {
        return null;
    }

    public boolean isSingleton() {
        return false;
    }

    public void setGroupId(String groupId) {
        this.groupId = groupId;
    }

    public void setRemoteGroupId(String remoteGroupId) {
        this.remoteGroupId = remoteGroupId;
    }


    public void setTransportEnum(TransportEnum transportEnum) {
        this.transportEnum = transportEnum;
    }

    public void setSerializer(AbstractSerializer serializer) {
        this.serializer = serializer;
    }

    public void setServiceIface(Class<?> serviceIface) {
        this.serviceIface = serviceIface;
    }

    public void afterPropertiesSet() throws Exception {
        client = TransportUtils.getClient(transportEnum);
        client.init(serializer, 10000L);
    }
}
