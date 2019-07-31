package com.gitee.apiconf.netty;

import com.gitee.apiconf.common.ChannelContext;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelId;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

public class HeartBeatServerHandler extends ChannelInboundHandlerAdapter {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static Map<ChannelId, AtomicInteger> channelLossConnectCount = new ConcurrentHashMap<>(8);

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent event = (IdleStateEvent) evt;
            if (event.state() == IdleState.READER_IDLE) {
                Channel channel = ctx.channel();
                AtomicInteger lossCount = channelLossConnectCount.get(channel.id());
                if (lossCount == null) {
                    lossCount = new AtomicInteger();
                    channelLossConnectCount.put(channel.id(), lossCount);
                }
                int count = lossCount.incrementAndGet();
                logger.warn("5秒没有接收到客户端的信息了");
                if (count > 2) {
                    logger.warn("关闭不活跃的channel,{}", channel.remoteAddress());
                    channelLossConnectCount.remove(channel.id());
                    ChannelContext.removeChannel(channel);
                    channel.close();
                }
            }
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }
}