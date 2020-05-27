package org.dbchain.blockchain.socket.distruptor.base;

import com.lmax.disruptor.EventFactory;

/**
 * @author zhaobo create on 2020/4/20.
 */
public class BaseEventFactory implements EventFactory<BaseEvent> {
    @Override
    public BaseEvent newInstance() {
        return new BaseEvent();
    }

}
