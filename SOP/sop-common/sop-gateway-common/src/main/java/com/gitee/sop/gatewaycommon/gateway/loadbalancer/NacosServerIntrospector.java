package com.gitee.sop.gatewaycommon.gateway.loadbalancer;

import com.alibaba.cloud.nacos.ribbon.NacosServer;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.netflix.ribbon.DefaultServerIntrospector;

import java.util.Map;

/**
 * @author tanghc
 */
public class NacosServerIntrospector extends DefaultServerIntrospector {

    @Override
    public Map<String, String> getMetadata(Server server) {
        if (server instanceof NacosServer) {
            NacosServer discoveryServer = (NacosServer)server;
            return discoveryServer.getInstance().getMetadata();
        } else {
            return super.getMetadata(server);
        }
    }
}
