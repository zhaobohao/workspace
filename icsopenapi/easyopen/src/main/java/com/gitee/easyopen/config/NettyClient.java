package com.gitee.easyopen.config;

import java.util.List;
import java.util.Map;
import java.util.concurrent.TimeUnit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gitee.easyopen.ManagerInitializer;

import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.timeout.IdleStateHandler;

/**
 * netty客户端
 * @author tanghc
 */
public class NettyClient {
	private static Logger logger = LoggerFactory.getLogger(NettyClient.class);
	
    private EventLoopGroup loop = new NioEventLoopGroup();

    private Map<String, NettyProcessor> processorMap;
    private List<ManagerInitializer> initializers;
    
    private String host;
    private int port;
    private String docUrl;

    public NettyClient(ConfigClient configClient, String host, int port) {
        this.processorMap = configClient.getProcessorMap();
        this.initializers = configClient.getInitializers();
        this.host = host;
        this.port = port;
        this.docUrl = configClient.getLocalServerPort();
    }

    public Bootstrap createBootstrap(Bootstrap bootstrap, EventLoopGroup eventLoop, boolean wait) {
        if (bootstrap != null) {
        	logger.debug("连接Netty服务器，serverIp:{}, serverPort:{}", this.host, this.port);
            final NettyClientHandler handler = new NettyClientHandler(this);
            bootstrap.group(eventLoop);
            bootstrap.channel(NioSocketChannel.class);
            bootstrap.option(ChannelOption.SO_KEEPALIVE, true);
            bootstrap.handler(new ChannelInitializer<SocketChannel>() {
                @Override
                protected void initChannel(SocketChannel socketChannel) throws Exception {
                    ChannelPipeline pipeline = socketChannel.pipeline();
                    pipeline.addLast("ping", new IdleStateHandler(0, 4, 0, TimeUnit.SECONDS));
                    // 添加Jboss的序列化，编解码工具
                    pipeline.addLast("decoder", MarshallingCodeCFactory.buildMarshallingDecoder());
                    pipeline.addLast("encoder", MarshallingCodeCFactory.buildMarshallingEncoder());
                    pipeline.addLast("handler", handler);
                    pipeline.addLast(new HeartBeatClientHandler());
                }
            });
            bootstrap.remoteAddress(this.host, this.port);
            bootstrap.connect().addListener(new ConnectionListener(this));
        }
        return bootstrap;
    }


    public void run() {
        createBootstrap(new Bootstrap(), loop, true);
    }

    public void reconnect(EventLoopGroup loop) {
        this.createBootstrap(new Bootstrap(), loop, false);
    }

    public Map<String, NettyProcessor> getProcessorMap() {
        return processorMap;
    }

    public List<ManagerInitializer> getInitializers() {
        return initializers;
    }

    public EventLoopGroup getLoop() {
        return loop;
    }

    public String getLocalServerPort() {
        return docUrl;
    }
    
}
