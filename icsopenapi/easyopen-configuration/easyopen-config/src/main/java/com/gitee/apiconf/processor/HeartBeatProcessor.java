package com.gitee.apiconf.processor;

import com.gitee.apiconf.common.ChannelContext;
import com.gitee.easyopen.config.ConfigMsg;
import io.netty.channel.Channel;

public class HeartBeatProcessor extends AbstractNettyServerProcessor {
    @Override
    public void process(Channel channel, ConfigMsg msg) {
        logger.debug("收到心跳包,app:{},channel:{}", msg.getApp(), channel.remoteAddress());
        ChannelContext.saveChannel(msg.getApp(), channel);
    }
}
