package com.gitee.apiconf.processor;

import java.util.List;

import com.gitee.apiconf.common.SpringContext;
import com.gitee.apiconf.entity.LimitAppConfig;
import com.gitee.apiconf.mapper.LimitAppConfigMapper;
import com.gitee.easyopen.config.ConfigMsg;

import io.netty.channel.Channel;

public class DownloandLimitConfigServerProcessor extends AbstractNettyServerProcessor {
    @Override
    public void process(Channel channel, ConfigMsg msg) {
        List<LimitAppConfig> list = SpringContext.getBean(LimitAppConfigMapper.class).listByColumn("app", msg.getApp());

        this.writeAndFlush(channel, msg.getCode(), list);
    }
}
