package com.gitee.sop.gatewaycommon.manager;

import com.gitee.sop.gatewaycommon.bean.ChannelMsg;

/**
 * @author tanghc
 */
public interface ChannelMsgProcessor {
    default void process(ChannelMsg channelMsg) {
    }
}
