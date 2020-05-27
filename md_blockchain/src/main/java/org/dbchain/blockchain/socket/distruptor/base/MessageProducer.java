package org.dbchain.blockchain.socket.distruptor.base;

/**
 * @author zhaobo create on 2020/4/20.
 */
public interface MessageProducer {
    void publish(BaseEvent baseEvent);
}
