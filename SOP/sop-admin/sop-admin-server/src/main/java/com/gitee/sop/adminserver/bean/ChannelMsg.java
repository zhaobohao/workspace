package com.gitee.sop.adminserver.bean;

import com.gitee.sop.adminserver.common.ChannelOperation;
import lombok.Data;
import org.apache.commons.lang3.time.DateFormatUtils;

import java.util.Date;

/**
 * @author tanghc
 */
@Data
public class ChannelMsg {

    private static final String TIME_PATTERN = "yyyy-MM-dd HH:mm:ss:SSS";

    public ChannelMsg(ChannelOperation channelOperation, Object data) {
        this.operation = channelOperation.getOperation();
        this.data = data;
        this.timestamp = DateFormatUtils.format(new Date(), TIME_PATTERN);
    }

    private String operation;
    private Object data;

    /**
     * 加个时间戳，格式yyyy-MM-dd HH:mm:ss:SSS，确保每次推送内容都不一样
     * nacos监听基于MD5值，如果每次推送的内容一样，则监听不会触发，因此必须确保每次推送的MD5不一样
     */
    private String timestamp;
}
