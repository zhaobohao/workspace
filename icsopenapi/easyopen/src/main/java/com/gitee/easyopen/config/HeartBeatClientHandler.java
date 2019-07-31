package com.gitee.easyopen.config;

import com.gitee.easyopen.ApiContext;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * netty心跳检测
 * @author tanghc
 */
public class HeartBeatClientHandler extends ChannelInboundHandlerAdapter {
    protected Logger logger = LoggerFactory.getLogger(getClass());

    private static ConfigMsg CONFIG_MSG;
    static {
        ConfigMsg configMsg = new ConfigMsg();
        configMsg.setApp(ApiContext.getAppName());
        configMsg.setCode(NettyOpt.HEART_BEAT.getCode());
        CONFIG_MSG = configMsg;
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.WRITER_IDLE) {
                logger.debug("客户端发送心跳包");
                ctx.channel().writeAndFlush(CONFIG_MSG);
            }
        }
    }
}