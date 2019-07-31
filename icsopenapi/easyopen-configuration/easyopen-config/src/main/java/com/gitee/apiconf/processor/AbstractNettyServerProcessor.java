package com.gitee.apiconf.processor;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.config.ConfigMsg;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public abstract class AbstractNettyServerProcessor implements NettyServerProcessor {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    protected void writeAndFlush(Channel channel, String code, Object data) {
        ConfigMsg msg = new ConfigMsg();
        msg.setCode(code);
        msg.setData(JSON.toJSONString(data));
        channel.writeAndFlush(msg);
    }
}
