package com.gitee.apiconf.netty;

import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sun.misc.Signal;
import sun.misc.SignalHandler;

/**
 * 启动后监听
 *
 * @author tanghc
 */
public class StartupListener implements ChannelFutureListener {
    private Logger logger = LoggerFactory.getLogger(getClass());

    private NettyServer nettyServer;

    public StartupListener(NettyServer nettyServer) {
        this.nettyServer = nettyServer;
    }

    @Override
    public void operationComplete(ChannelFuture future) throws Exception {
        this.addShutdownListner();
    }

    // 进程关闭后监听
    protected void addShutdownListner() {
        SignalHandler signalHandler = new SignalHandler() {
            @Override
            public void handle(Signal signal) {
                logger.info("收到进程信号量：{}", signal.getName());
                Thread t = new Thread(new ShutdownHook(), "ShutdownHookServer-Thread");
                Runtime.getRuntime().addShutdownHook(t);
                Runtime.getRuntime().exit(0);
            }
        };
        Signal.handle(new Signal(getOSSignalKill()), signalHandler);
    }

    class ShutdownHook implements Runnable {
        @Override
        public void run() {
            logger.info("进程退出，关闭Netty服务端...");
            nettyServer.shutdown();
        }
    }

    private String getOSSignalKill() {
        return System.getProperties().getProperty("os.name").
                toLowerCase().startsWith("win")
                ? System.getProperty("term.sig", "INT") //windows下 Ctrl + C
                : System.getProperty("term.sig", "USR2"); // Linux下（等价于kill -12 pid
    }
}
