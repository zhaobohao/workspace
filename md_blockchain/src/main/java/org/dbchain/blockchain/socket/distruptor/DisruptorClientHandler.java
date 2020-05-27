package org.dbchain.blockchain.socket.distruptor;

import org.dbchain.blockchain.ApplicationContextProvider;
import org.dbchain.blockchain.socket.distruptor.base.BaseEvent;
import com.lmax.disruptor.EventHandler;

/**
 * @author zhaobo create on 2020/4/20.
 */
public class DisruptorClientHandler implements EventHandler<BaseEvent> {

    @Override
    public void onEvent(BaseEvent baseEvent, long sequence, boolean endOfBatch) throws Exception {
        ApplicationContextProvider.getBean(DisruptorClientConsumer.class).receive(baseEvent);
    }
}
