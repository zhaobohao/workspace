package com.gitee.sop.gatewaycommon;

import com.gitee.sop.gatewaycommon.util.LoadBalanceUtil;
import junit.framework.TestCase;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 轮询选择一台机器。
 *
 * @author tanghc
 */
public class RoundRobinTest extends TestCase {

    public void testDo() {
        String serviceId = "order-service";
        List<String> serverList = new ArrayList<>(Arrays.asList("server1", "server2", "server3"));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
    }

    public void testAdd() {
        String serviceId = "order-service";
        List<String> serverList = new ArrayList<>(Arrays.asList("server1", "server2", "server3"));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        // 中途添加一个服务器
        serverList.add("server4");
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
    }

    public void testRemove() {
        String serviceId = "order-service";
        List<String> serverList = new ArrayList<>(Arrays.asList("server1", "server2", "server3"));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        // 中途减少一台服务器
        serverList.remove(2);
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
        System.out.println(chooseRoundRobinServer(serviceId, serverList));
    }

    /**
     * 轮询选择一台机器。
     * 假设有N台服务器：S = {S1, S2, …, Sn}，一个指示变量i表示上一次选择的服务器ID。变量i被初始化为N-1。
     *
     * @param serviceId serviceId
     * @param servers   服务器列表
     * @return 返回一台服务器实例
     */
    private <T> T chooseRoundRobinServer(String serviceId, List<T> servers) {
        return LoadBalanceUtil.chooseByRoundRobin(serviceId, servers);
    }
}
