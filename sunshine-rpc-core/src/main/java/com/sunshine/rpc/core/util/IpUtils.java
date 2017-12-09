package com.sunshine.rpc.core.util;

import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author sunshine1027 [sunshine10271993@gmail.com]
 */
public class IpUtils {
    public static String getIp() {
        try {
            return InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            return "";
        }
    }
}
