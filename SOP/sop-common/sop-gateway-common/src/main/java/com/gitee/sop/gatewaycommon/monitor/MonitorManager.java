package com.gitee.sop.gatewaycommon.monitor;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;

/**
 * @author tanghc
 */
public class MonitorManager {

    private static Map<String, MonitorInfo> monitorMap = new ConcurrentHashMap<>(128);

    public Map<String, MonitorInfo> getMonitorData() {
        return monitorMap;
    }

    public MonitorInfo getMonitorInfo(String routeId, Function<String, MonitorInfo> createFun) {
        return monitorMap.computeIfAbsent(routeId, createFun);
    }

}
