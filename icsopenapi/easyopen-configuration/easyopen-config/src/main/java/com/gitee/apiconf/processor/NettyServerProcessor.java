package com.gitee.apiconf.processor;

import com.gitee.easyopen.config.ConfigMsg;

import io.netty.channel.Channel;

public interface NettyServerProcessor {
    void process(Channel channel, ConfigMsg msg);
}