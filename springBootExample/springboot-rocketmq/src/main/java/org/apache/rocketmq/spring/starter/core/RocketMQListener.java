package org.apache.rocketmq.spring.starter.core;

public interface RocketMQListener<T> {
    void onMessage(T message);
}
