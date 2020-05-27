package org.dbchain.blockchain.core.event;

import org.springframework.context.ApplicationEvent;

/**
 * 同步block到sqlite事件
 * @author zhaobo create on 2020/3/21.
 */
public class DbSyncEvent extends ApplicationEvent {
    public DbSyncEvent(Object source) {
        super(source);
    }
}
