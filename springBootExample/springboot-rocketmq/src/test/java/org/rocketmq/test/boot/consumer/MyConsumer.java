package org.rocketmq.test.boot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;

/**
 * 演示接收String对像
 */
@Slf4j
//@RocketMQMessageListener(topic = "test-topic-1", consumerGroup = "my-consumer_test-topic-1")
public class MyConsumer implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received message: " + message);
    }
}