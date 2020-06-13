package org.apache.rocketmq.spring.starter.core;

public interface RocketMQConsumerLifecycleListener<T> {
    void prepareStart(final T consumer);
}
