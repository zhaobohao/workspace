package com.gitee.sop.gatewaycommon.loadbalancer;

import com.netflix.loadbalancer.Server;
import com.netflix.niws.loadbalancer.DiscoveryEnabledServer;

import java.util.Map;

/**
 * @author tanghc
 */
public class EurekaEnvironmentServerChooser extends EnvironmentServerChooser {
    @Override
    protected Map<String, String> getMetada(Server server) {
       return ((DiscoveryEnabledServer) server).getInstanceInfo().getMetadata();
    }
}
