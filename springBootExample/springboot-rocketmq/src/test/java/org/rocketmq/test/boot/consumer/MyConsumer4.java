package org.rocketmq.test.boot.consumer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.spring.starter.annotation.RocketMQMessageListener;
import org.apache.rocketmq.spring.starter.core.RocketMQListener;
import org.apache.rocketmq.spring.starter.enums.ConsumeMode;

/**
 * 顺序消息消费失败，默认不重试
 * 注意：Consumer里抛出异常才会重试，所以使用者不要把Consumer里的整个代码try-catch
 */
@Slf4j
//@RocketMQMessageListener(topic = "test-topic-4", consumerGroup = "my-consumer_test-topic-4",consumeMode = ConsumeMode.ORDERLY)
public class MyConsumer4 implements RocketMQListener<String> {
    @Override
    public void onMessage(String message) {
        log.info("received message: " + message);
        int a = 1 / 0;
    }
}