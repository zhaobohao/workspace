package com.gitee.sop.gatewaycommon.bean;

import com.gitee.sop.gatewaycommon.manager.ChannelMsgProcessor;

/**
 * @author tanghc
 */
public interface ServiceBeanInitializer extends ChannelMsgProcessor {

    void load(String serviceId);
}
