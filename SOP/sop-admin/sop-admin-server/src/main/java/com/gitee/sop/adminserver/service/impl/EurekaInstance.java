package com.gitee.sop.adminserver.service.impl;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
 * {
 * 	"instance": {
 * 		"instanceId": "demo-order2:11101",
 * 		"app": "demo-order2",
 * 		"appGroutName": null,
 * 		"ipAddr": "127.0.0.1",
 * 		"sid": "na",
 * 		"homePageUrl": null,
 * 		"statusPageUrl": null,
 * 		"healthCheckUrl": null,
 * 		"secureHealthCheckUrl": null,
 * 		"vipAddress": "demo-order2",
 * 		"secureVipAddress": "demo-order2",
 * 		"countryId": 1,
 * 		"dataCenterInfo": {
 * 			"@class": "com.netflix.appinfo.InstanceInfo$DefaultDataCenterInfo",
 * 			"name": "MyOwn"
 *                },
 * 		"hostName": "127.0.0.1",
 * 		"status": "UP",
 * 		"leaseInfo": null,
 * 		"isCoordinatingDiscoveryServer": false,
 * 		"lastUpdatedTimestamp": 1529391461000,
 * 		"lastDirtyTimestamp": 1529391461000,
 * 		"actionType": null,
 * 		"asgName": null,
 * 		"overridden_status": "UNKNOWN",
 * 		"port": {
 * 			"$": 11102,
 * 			"@enabled": "false"
 *        },
 * 		"securePort": {
 * 			"$": 7002,
 * 			"@enabled": "false"
 *        },
 * 		"metadata": {
 * 			"@class": "java.util.Collections$EmptyMap"
 *        }* 	}
 * }
 * @author tanghc
 */
@Data
public class EurekaInstance {
    private String instanceId;
    private String ipAddr;
    private Map<String, Object> port;

    private String status;
    private String statusPageUrl;
    private String healthCheckUrl;
    private String lastUpdatedTimestamp;

    private Map<String, String> metadata;

    public String fetchPort() {
        if (CollectionUtils.isEmpty(port)) {
            return "";
        }
        return String.valueOf(port.getOrDefault("$", ""));
    }

}
