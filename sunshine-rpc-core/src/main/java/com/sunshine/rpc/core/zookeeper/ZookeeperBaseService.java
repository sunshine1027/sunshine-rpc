package com.sunshine.rpc.core.zookeeper;

import com.sunshine.rpc.core.util.PropertiesUtils;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

import static com.sunshine.rpc.core.BaseConstants.NODE_PATH_KEY;
import static com.sunshine.rpc.core.BaseConstants.ZK_SERVERS_KEY;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class ZookeeperBaseService {
    static ZooKeeper zooKeeper;

    protected static String zkServers = PropertiesUtils.getProperties(ZK_SERVERS_KEY);
    protected static String nodePath = PropertiesUtils.getProperties(NODE_PATH_KEY);

    public static void initZookeeper() {
        getZooKeeperInstance();
    }

    static ZooKeeper getZooKeeperInstance() {
        System.out.println();
        if (zooKeeper == null) {
            try {
                zooKeeper = new ZooKeeper(zkServers, 20000, new Watcher() {
                    public void process(WatchedEvent watchedEvent) {
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return zooKeeper;
    }

    protected static String buildNodeKey(String groupId, String iface) {
        return "/sunshine-rpc/" + groupId + "/" + iface;
    }

    protected static String buildServerAddress(String groupId, String iface, String ip, int port) {
        return "/sunshine-rpc/" + groupId + "/" + iface + "/" + ip + ":" + port;
    }

}
