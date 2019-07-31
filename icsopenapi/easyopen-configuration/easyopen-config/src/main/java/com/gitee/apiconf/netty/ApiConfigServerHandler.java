package com.gitee.apiconf.netty;

import com.gitee.apiconf.common.ChannelContext;
import com.gitee.apiconf.processor.ClientConnectedProcessor;
import com.gitee.apiconf.processor.DownloandLimitConfigServerProcessor;
import com.gitee.apiconf.processor.DownloandPermissionConfigServerProcessor;
import com.gitee.apiconf.processor.DownloandSecretConfigServerProcessor;
import com.gitee.apiconf.processor.HeartBeatProcessor;
import com.gitee.apiconf.processor.NettyServerProcessor;
import com.gitee.apiconf.processor.SyncAppApiProcessor;
import com.gitee.easyopen.config.ConfigMsg;
import com.gitee.easyopen.config.NettyOpt;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;

public class ApiConfigServerHandler extends SimpleChannelInboundHandler<ConfigMsg> {
    private Logger logger = LoggerFactory.getLogger(getClass());
    
    private static Map<String, NettyServerProcessor> processorMap = new HashMap<>();
    static {
        processorMap.put(NettyOpt.DOWNLOAD_LIMIT_CONFIG.getCode() , new DownloandLimitConfigServerProcessor());
        processorMap.put(NettyOpt.CLIENT_CONNECTED.getCode() , new ClientConnectedProcessor());
        processorMap.put(NettyOpt.DOWNLOAD_PERMISSION_CONFIG.getCode(), new DownloandPermissionConfigServerProcessor());
        processorMap.put(NettyOpt.SYNC_APP_API.getCode(), new SyncAppApiProcessor());
        processorMap.put(NettyOpt.DOWNLOAD_SECRET_CONFIG.getCode(), new DownloandSecretConfigServerProcessor());
        processorMap.put(NettyOpt.HEART_BEAT.getCode(), new HeartBeatProcessor());
    }
    

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ConfigMsg configMsg) throws Exception {
        String code = configMsg.getCode();

        NettyServerProcessor processor = processorMap.get(code);
        if(processor != null) {
            processor.process(ctx.channel(), configMsg);
        }
    }
    

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        logger.info("client:{} 已连接", incoming.remoteAddress());
        ctx.fireChannelActive();
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        Channel incoming = ctx.channel();
        logger.info("client:{} 失去连接", incoming.remoteAddress());
        ChannelContext.removeChannel(incoming);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error("服务端出错", cause);
    }
    
    

}
