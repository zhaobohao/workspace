package com.gitee.easyopen.config;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.bean.Api;
import com.gitee.easyopen.bean.DefinitionHolder;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.EventLoop;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

/**
 * @author tanghc
 */
public class NettyClientHandler extends SimpleChannelInboundHandler<ConfigMsg> {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private NettyClient nettyClient;
    private Map<String, NettyProcessor> processorMap;

    private String docUrl;

    public NettyClientHandler(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
        this.processorMap = nettyClient.getProcessorMap();
        this.docUrl = nettyClient.getLocalServerPort();
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.info("连接配置中心成功");
        Channel channel = ctx.channel();
        this.registerServer(channel);
        this.syncAppApiInfi(channel);
        this.updateConfigFromServer(channel);
    }

    public void updateConfigFromServer(final Channel channel) {
        logger.info("----开始同步配置中心数据----");
        try {
            downloadSecretConfig(channel);
            downloadPermissionConfig(channel);
            downloadLimitConfig(channel);
        } catch (Exception e) {
            logger.error("同步配置中心数据错误", e);
        }
    }

    public void syncAppApiInfi(Channel channel) {
        logger.info("同步本地API到配置中心");
        ConfigMsg syncMsg = newConfigMsg(NettyOpt.SYNC_APP_API);
        List<Api> allApi = DefinitionHolder.listAllApi();
        syncMsg.setData(JSON.toJSONString(allApi));
        channel.writeAndFlush(syncMsg);
    }

    public void registerServer(Channel channel) {
        ConfigMsg msg = newConfigMsg(NettyOpt.CLIENT_CONNECTED);
        ApiConfig apiConfig = ApiContext.getApiConfig();
        App app = new App();
        app.setApp(msg.getApp());
        app.setDocPassword(apiConfig.getDocPassword());
        app.setDocStatus(apiConfig.isShowDoc() ? (byte)1 : 0);
        app.setDocUrl(this.docUrl);
        msg.setData(JSON.toJSONString(app));
        channel.writeAndFlush(msg);
    }

    public ConfigMsg newConfigMsg(NettyOpt nettyOpt) {
        ConfigMsg msg = new ConfigMsg();
        msg.setApp(ApiContext.getAppName());
        msg.setCode(nettyOpt.getCode());
        return msg;
    }

    public void downloadSecretConfig(Channel channel) {
        logger.info("下载秘钥配置");
        ConfigMsg msg = this.newConfigMsg(NettyOpt.DOWNLOAD_SECRET_CONFIG);
        channel.writeAndFlush(msg);
    }

    public void downloadPermissionConfig(Channel channel) {
        logger.info("下载权限配置");
        ConfigMsg msg = this.newConfigMsg(NettyOpt.DOWNLOAD_PERMISSION_CONFIG);
        channel.writeAndFlush(msg);
    }

    public void downloadLimitConfig(Channel channel) {
        logger.info("下载限流配置");
        ConfigMsg msg = this.newConfigMsg(NettyOpt.DOWNLOAD_LIMIT_CONFIG);
        channel.writeAndFlush(msg);
    }

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ConfigMsg configMsg) throws Exception {
        String code = configMsg.getCode();
        NettyProcessor processor = processorMap.get(code);
        if (processor == null) {
            throw new RuntimeException("错误的code:" + code);
        }
        processor.process(ctx.channel(), configMsg.getData());
    }

    /** 掉线 */
    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        final EventLoop eventLoop = ctx.channel().eventLoop();
        /*
         如果按下Ctrl+C，打印：isShutdown:false,isTerminated:false, isShuttingDown:true
         logger.info("isShutdown:{},isTerminated:{}, isShuttingDown:{}", eventLoop.isShutdown(), eventLoop.isTerminated(), eventLoop.isShuttingDown());
         正准备关闭，不需要再重启了
         */
        if (eventLoop.isShuttingDown()) {
            super.channelInactive(ctx);
            return;
        }
        logger.info("已断开与服务端的链接，尝试重连...");
        eventLoop.schedule(new Runnable() {
            @Override
            public void run() {
                nettyClient.reconnect(eventLoop);
            }
        }, 5L, TimeUnit.SECONDS);
        super.channelInactive(ctx);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        if (cause instanceof StartupException) {
            logger.error("Netty客户端启动失败:", cause);
            ctx.channel().close();
            System.exit(0);
        } else {
            logger.error("Netty错误", cause);
            super.exceptionCaught(ctx, cause);
        }

    }
}