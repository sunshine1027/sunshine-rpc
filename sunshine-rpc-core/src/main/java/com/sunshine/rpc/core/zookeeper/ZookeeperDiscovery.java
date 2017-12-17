package com.sunshine.rpc.core.zookeeper;


import com.sunshine.rpc.core.transport.SunshineClientProxy;
import com.sunshine.rpc.core.util.BeanUtils;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.util.CollectionUtils;

import java.util.*;
import java.util.concurrent.*;

/**
 * 节点名：/sunshine-rpc/groupId/iface/ip:port
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class ZookeeperDiscovery extends ZookeeperBaseService implements InitializingBean {
    private static Random random = new Random();
    public static ConcurrentHashMap<String, List<String>> groupId_iface_serverAddress = new ConcurrentHashMap<String, List<String>>();
    private ScheduledExecutorService scheduleService;

    public static void freshServiceAddress(){
        try {
            // iface list
            Map<String, SunshineClientProxy> clientProxyMap = BeanUtils.getBeans(SunshineClientProxy.class);
            for (Map.Entry<String, SunshineClientProxy> entry : clientProxyMap.entrySet()) {
                SunshineClientProxy clientProxy = entry.getValue();
                List<String> ipPortList = getZooKeeperInstance().getChildren(buildNodeKey(clientProxy.getGroupId(), clientProxy.getServiceIface().getName()), true);
                groupId_iface_serverAddress.put(buildKey(clientProxy.getGroupId(), clientProxy.getServiceIface().getName()), ipPortList);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static String buildKey(String groupId, String iface) {
        return groupId + "_" + iface;
    }
    public static String discover(String groupId, String iface) {
        List<String> addressList = groupId_iface_serverAddress.get(buildKey(groupId, iface));
        if (CollectionUtils.isEmpty(addressList))
            throw new RuntimeException("connect list is empty.");
        int size = addressList.size();
        return addressList.get(random.nextInt(size));
    }



    public void afterPropertiesSet() throws Exception {
        scheduleService = Executors.newScheduledThreadPool(1);
        //5秒同步一次zk
        scheduleService.scheduleAtFixedRate(new Runnable() {
            public void run() {
                freshServiceAddress();
            }
        }, 0L, 5, TimeUnit.SECONDS);
    }
}
