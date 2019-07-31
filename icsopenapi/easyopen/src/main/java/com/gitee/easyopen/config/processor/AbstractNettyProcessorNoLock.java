package com.gitee.easyopen.config.processor;

import com.gitee.easyopen.config.ConfigClient;
import com.gitee.easyopen.config.NettyOpt;

/**
 * @author tanghc
 */
public abstract class AbstractNettyProcessorNoLock extends AbstractNettyProcessor {

    public AbstractNettyProcessorNoLock(ConfigClient configClient, NettyOpt nettyOpt) {
        super(configClient, nettyOpt);
    }

    @Override
    protected final boolean hasLock() {
        return false;
    }
}
