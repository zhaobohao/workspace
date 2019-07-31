package com.gitee.easyopen.config.processor;

import com.gitee.easyopen.config.ConfigClient;
import com.gitee.easyopen.config.CountDownLatchManager;
import com.gitee.easyopen.config.NettyOpt;
import com.gitee.easyopen.config.NettyProcessor;
import io.netty.channel.Channel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tanghc
 */
public abstract class AbstractNettyProcessor implements NettyProcessor {

    protected Logger logger = LoggerFactory.getLogger(getClass());

    private volatile static int lockObjectCount = 0;

    protected ConfigClient configClient;
    protected NettyOpt nettyOpt;

    public AbstractNettyProcessor(ConfigClient configClient, NettyOpt nettyOpt) {
        super();
        this.configClient = configClient;
        this.nettyOpt = nettyOpt;
        if (this.hasLock()) {
            lockObjectCount++;
        }
    }

    /**
     * 执行netty操作
     * @param channel channel
     * @param data 数据
     */
    protected abstract void doProcess(Channel channel, String data);

    protected boolean hasLock() {
        return true;
    }

    @Override
    public synchronized void process(final Channel channel, final String data) {
        try {
            doProcess(channel, data);
        } finally {
            if (hasLock()) {
                CountDownLatchManager.countDown();
            }
        }
    }

    public String getCode() {
        return this.nettyOpt.getCode();
    }

    public static int getLockObjectCount() {
        return lockObjectCount;
    }
}
