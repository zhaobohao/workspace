package com.gitee.sop.gatewaycommon.route;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class ServiceHolder {
    private String serviceId;
    private long lastUpdatedTimestamp;
}