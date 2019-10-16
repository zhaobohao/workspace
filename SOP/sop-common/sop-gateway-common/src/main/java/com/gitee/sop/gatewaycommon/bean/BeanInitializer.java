package com.gitee.sop.gatewaycommon.bean;

import com.gitee.sop.gatewaycommon.manager.ChannelMsgProcessor;

/**
 * @author tanghc
 */
public interface BeanInitializer extends ChannelMsgProcessor {
    /**
     * 执行加载操作
     */
    void load();
}
