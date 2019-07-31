package com.gitee.easyopen.config;

import java.util.List;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.gitee.easyopen.ManagerInitializer;

import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.EventLoop;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * netty连接监听
 * @author tanghc
 */
public class ConnectionListener implements ChannelFutureListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private static volatile long DELAY_SECONDS = 5L;
    private static volatile boolean firstFail = true;
    private static AtomicInteger tryTimes = new AtomicInteger();
    
    private NettyClient nettyClient;

    public ConnectionListener(NettyClient nettyClient) {
        this.nettyClient = nettyClient;
    }

    /**
     * 连接回调
     *
     * @param channelFuture
     * @throws Exception
     */
    @Override
    public void operationComplete(ChannelFuture channelFuture) throws Exception {
        // 链接成功
        if (channelFuture.isSuccess()) {
            tryTimes.set(0);
            firstFail = false;
            Channel channel = channelFuture.channel();
            EventLoop loop = channel.eventLoop();
            this.addShutdownListner(loop);
        } else { // 链接失败，尝试重新连接
            logger.info("Netty客户端连接服务器失败，尝试重连({})", tryTimes.incrementAndGet());
            final EventLoop loop = channelFuture.channel().eventLoop();

            // 如果是第一次失败，还是要初始化一下
            if (firstFail) {
                try {
                    this.fireLocalConfig(this.nettyClient.getInitializers());
                    firstFail = false;
                } catch (Exception e) {
                    channelFuture.channel().pipeline().fireExceptionCaught(new StartupException(e));
                }
            }

            // 进行重连
            loop.schedule(new Runnable() {
                @Override
                public void run() {
                    nettyClient.reconnect(loop);
                }
            }, DELAY_SECONDS, TimeUnit.SECONDS);
        }
    }

    public void fireLocalConfig(List<ManagerInitializer> initializers) {
        logger.info("Netty客户端无法连接到服务器，尝试加载本地配置文件");
        for (ManagerInitializer managerInitializer : initializers) {
            managerInitializer.loadLocal();
        }
    }

    /** 进程关闭后监听 */
    protected void addShutdownListner(EventLoop loop) {
        SignalHandler signalHandler = new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                logger.info("收到进程信号量：{}",signal.getName());
                Thread t = new Thread(new ShutdownHook(loop), "NettyClient-ShutdownHook-Thread");
                Runtime.getRuntime().addShutdownHook(t);
                Runtime.getRuntime().exit(0);
            }
        };
        Signal.handle(new Signal(getOSSignalKill()), signalHandler);
    }
    
    
    class ShutdownHook implements Runnable {
        private EventLoop loop;

        public ShutdownHook(EventLoop loop) {
            this.loop = loop;
        }

        @Override
        public void run() {
            logger.info("进程退出，关闭Netty客户端...");
            loop.shutdownGracefully();
        }
    }

    private String getOSSignalKill() {
        return System.getProperties().getProperty("os.name").
                toLowerCase().startsWith("win")
                //windows下 Ctrl + C
                ? System.getProperty("term.sig", "INT")
                // Linux下等价于kill -12 pid
                : System.getProperty("term.sig", "USR2");
    }

}