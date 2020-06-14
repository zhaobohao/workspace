package org.rocketmq.test.boot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;

/**
 * 演示接收String对像
 */
@Slf4j
@RocketMQMessageListener(topic = "RMQ_SYS_TRACE_TOPIC", consumerGroup = "my-consumer_traces")
public class MyConsumer6 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received trace message: " + message);
    }
}