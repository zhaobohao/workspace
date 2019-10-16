package com.gitee.sop.gatewaycommon.bean;

import lombok.Data;

import java.util.Map;

/**
 * @author tanghc
 */
@Data
public class InstanceDefinition {
    private String instanceId;
    private String serviceId;
    private String ip;
    private int port;
    private Map<String, String> metadata;
}
