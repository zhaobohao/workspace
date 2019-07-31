package com.gitee.easyopen.config;

import io.netty.channel.Channel;

/**
 * @author tanghc
 */
public interface NettyProcessor {

    /**
     * 执行netty操作
     * @param channel channel
     * @param data 数据
     */
    void process(Channel channel, String data);
}
