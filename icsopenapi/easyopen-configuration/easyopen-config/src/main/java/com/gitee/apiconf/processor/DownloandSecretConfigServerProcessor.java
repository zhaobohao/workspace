package com.gitee.apiconf.processor;

import java.util.List;

import com.gitee.apiconf.common.SpringContext;
import com.gitee.apiconf.entity.PermClient;
import com.gitee.apiconf.entity.status.ClientStatus;
import com.gitee.apiconf.mapper.PermClientMapper;
import com.gitee.easyopen.config.ConfigMsg;

import com.gitee.fastmybatis.core.query.Query;
import io.netty.channel.Channel;

public class DownloandSecretConfigServerProcessor extends AbstractNettyServerProcessor {
    @Override
    public void process(Channel channel, ConfigMsg msg) {
        Query query = new Query();
        query.eq("app", msg.getApp()).eq("status", ClientStatus.ENABLE.getStatus());

        List<PermClient> list = SpringContext.getBean(PermClientMapper.class).list(query);
        this.writeAndFlush(channel, msg.getCode(), list);
    }
}
