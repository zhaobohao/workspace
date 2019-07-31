package com.gitee.apiconf.processor;

import java.util.List;

import com.gitee.apiconf.common.SpringContext;
import com.gitee.apiconf.mapper.ApiInfoMapper;
import com.gitee.easyopen.config.ConfigMsg;
import com.gitee.easyopen.permission.ApiInfo;

import io.netty.channel.Channel;

public class DownloandPermissionConfigServerProcessor extends AbstractNettyServerProcessor {
    @Override
    public void process(Channel channel, ConfigMsg msg) {
        ApiInfoMapper mapper = SpringContext.getBean(ApiInfoMapper.class);
        List<ApiInfo> list = mapper.listAppAuth(msg.getApp());

        this.writeAndFlush(channel, msg.getCode(), list);
    }
}
