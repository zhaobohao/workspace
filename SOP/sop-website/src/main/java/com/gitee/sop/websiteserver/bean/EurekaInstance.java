package com.gitee.sop.websiteserver.bean;

import lombok.Data;
import org.springframework.util.CollectionUtils;

import java.util.Map;

/**
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

    public String fetchPort() {
        if (CollectionUtils.isEmpty(port)) {
            return "";
        }
        return String.valueOf(port.getOrDefault("$", ""));
    }

}
