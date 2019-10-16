package com.gitee.sop.gatewaycommon.bean;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class ServiceGrayDefinition {
    private String serviceId;
    private String instanceId;
    private String data;
}
