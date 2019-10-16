package com.gitee.sop.gatewaycommon.route;

import com.gitee.sop.gatewaycommon.bean.InstanceDefinition;

/**
 * @author tanghc
 */
public interface ServiceListener {
    void onRemoveService(String serviceId);

    void onAddInstance(InstanceDefinition instance);
}
