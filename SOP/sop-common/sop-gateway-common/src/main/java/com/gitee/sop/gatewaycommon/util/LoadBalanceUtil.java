package com.gitee.sop.gatewaycommon.util;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @author tanghc
 */
public class LoadBalanceUtil {

    /**
     * key：serviceId，value：指示变量i
     */
    private static Map<String, Integer> serviceIdRoundMap = new ConcurrentHashMap<>(8);

    /**
     * 轮询选择一台机器。<br>
     * <p>
     * 假设有N台服务器：S = {S1, S2, …, Sn}，一个指示变量i表示上一次选择的服务器ID。变量i被初始化为N-1。
     * </p>
     * 参考：https://blog.csdn.net/qq_37469055/article/details/87991327
     * @param serviceId serviceId，不同的serviceId对应的服务器数量不一样，需要区分开
     * @param servers   服务器列表
     * @return 返回一台服务器实例
     */
    public static <T> T chooseByRoundRobin(String serviceId, List<T> servers) {
        if (servers == null || servers.isEmpty()) {
            return null;
        }
        int n = servers.size();
        int i = serviceIdRoundMap.computeIfAbsent(serviceId, (k) -> n - 1);
        int j = i;
        do {
            j = (j + 1) % n;
            i = j;
            serviceIdRoundMap.put(serviceId, i);
            return servers.get(i);
        } while (j != i);
    }

    /**
     * 随机选取一台实例
     *
     * @param servers 服务列表
     * @return 返回实例，没有返回null
     */
    public static <T> T chooseByRandom(List<T> servers) {
        if (servers.isEmpty()) {
            return null;
        }
        int serverCount = servers.size();
        // 随机选取一台实例
        int index = chooseRandomInt(serverCount);
        return servers.get(index);
    }

    private static int chooseRandomInt(int serverCount) {
        return ThreadLocalRandom.current().nextInt(serverCount);
    }

}
