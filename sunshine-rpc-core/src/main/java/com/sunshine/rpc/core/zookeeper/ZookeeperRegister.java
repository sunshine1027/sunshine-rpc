package com.sunshine.rpc.core.zookeeper;

import com.sunshine.rpc.core.util.IpUtils;
import org.apache.zookeeper.*;
import org.apache.zookeeper.data.Stat;
import org.springframework.util.StringUtils;


/**
 * 节点名：/sunshine-rpc/groupId/iface/ip:port
 *
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class ZookeeperRegister extends ZookeeperBaseService {


    public static void registerServices(String groupId, int port, String iface) throws KeeperException, InterruptedException {
        // init address: ip : port
        String ip = IpUtils.getIp();
        if (ip == null) {
            return;
        }
        String serverAddress = ip + ":" + port;
        //检测基础路径有没有，没有则创建
        Stat stat = getZooKeeperInstance().exists(buildNodeKey(groupId, iface), true);
        if (stat == null) {
            createNode(buildNodeKey(groupId, iface));
        }
        String addressPath = buildNodeKey(groupId, iface) + "/" + serverAddress;
        // register ip:port
        Stat addreddStat = getZooKeeperInstance().exists(addressPath, true);
        if (addreddStat != null) {
            getZooKeeperInstance().delete(addressPath, -1);
        }
        getZooKeeperInstance().create(addressPath, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.EPHEMERAL);
        System.out.println("Register service on zookeeper success, interfaceName:" + iface + ", serverAddress:" + serverAddress + ", addressPath:" + addressPath);
    }


    /**
     * 如果节点不存在，循环创建
     *
     * @param path
     * @return
     */
    private static Stat createNode(String path) {
        if (StringUtils.isEmpty(path)) {
            return null;
        }

        try {
            Stat stat = getZooKeeperInstance().exists(path, true);
            if (stat == null) {
                //查看是否需要创建父节点
                if (path.lastIndexOf("/") > 0) {
                    String parentPath = path.substring(0, path.lastIndexOf("/"));
                    Stat parentStat = getZooKeeperInstance().exists(parentPath, true);
                    if (parentStat == null) {
                        createNode(parentPath);
                    }
                }
                // 创建节点
                zooKeeper.create(path, new byte[]{}, ZooDefs.Ids.OPEN_ACL_UNSAFE, CreateMode.PERSISTENT);
            }
            return getZooKeeperInstance().exists(path, true);
        } catch (KeeperException e) {
            e.printStackTrace();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return null;
    }
}
