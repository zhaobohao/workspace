package com.gitee.sop.gatewaycommon.bean;

import lombok.Data;

/**
 * @author tanghc
 */
@Data
public class GatewayPushDTO {
    private String dataId;
    private String groupId;
    private ChannelMsg channelMsg;

    public GatewayPushDTO() {
    }

    public GatewayPushDTO(String dataId, String groupId, ChannelMsg channelMsg) {
        this.dataId = dataId;
        this.groupId = groupId;
        this.channelMsg = channelMsg;
    }
}
