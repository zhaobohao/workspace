package org.rocketmq.test.boot.producer;

import lombok.extern.slf4j.Slf4j;
import org.apache.rocketmq.client.producer.SendCallback;
import org.apache.rocketmq.client.producer.SendResult;
import org.apache.rocketmq.spring.starter.core.RocketMQTemplate;
import org.apache.rocketmq.spring.starter.enums.MessageDelayLevel;
import org.rocketmq.test.boot.OrderPaidEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.messaging.support.MessageBuilder;

import java.math.BigDecimal;

@Slf4j
@SpringBootApplication
public class ProducerApplication implements CommandLineRunner {
    @Autowired
    private RocketMQTemplate rocketMQTemplate;
    
    public static void main(String[] args){
        SpringApplication.run(ProducerApplication.class, args);
    }
    
    @Override
    public void run(String... args) throws Exception {
        for (int i = 0; i < 5; i++) {
            try {
                rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
                System.out.println("Send OK!");
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        rocketMQTemplate.syncSend("test-topic-1", "Hello, World! I'm from simple message");
        rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));
        rocketMQTemplate.sendDelayed("test-topic-1", "I'm delayed message", MessageDelayLevel.TIME_1M);
        rocketMQTemplate.sendOneWay("test-topic-1", MessageBuilder.withPayload("I'm one way message").build());
        rocketMQTemplate.syncSendOrderly("test-topic-4", "I'm order message", "1234");
        rocketMQTemplate.asyncSend("test-topic-1", MessageBuilder.withPayload("I'm async message").build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {
                log.info("async: {}", sendResult);
            }

            @Override
            public void onException(Throwable e) {
                log.error(e.getMessage(), e);
            }
        });
        System.out.println("send finished!");
        // 如下两种方式等价
        rocketMQTemplate.convertAndSend("test-topic-1", "Hello, World!");
        rocketMQTemplate.send("test-topic-1", MessageBuilder.withPayload("Hello, World! I'm from spring message").build());
        // 第三个参数为key
        rocketMQTemplate.syncSend("test-topic-1", "Hello, World! I'm from simple message", "18122811143034568830");

        // topic: ORDER，tag: paid, cacel
        rocketMQTemplate.convertAndSend("ORDER:paid", "Hello, World!");
        rocketMQTemplate.convertAndSend("ORDER:cancel", "Hello, World!");

        // 消息体为自定义对象
        rocketMQTemplate.convertAndSend("test-topic-2", new OrderPaidEvent("T_001", new BigDecimal("88.00")));


        // 发送延迟消息
        rocketMQTemplate.sendDelayed("test-topic-1", "I'm delayed message", MessageDelayLevel.TIME_1M);

        // 发送即发即失消息（不关心发送结果）
        rocketMQTemplate.sendOneWay("test-topic-1", MessageBuilder.withPayload("I'm one way message").build());


        // 发送顺序消息
        rocketMQTemplate.syncSendOrderly("test-topic-4", "I'm order message", "1234");

        // 发送异步消息
        rocketMQTemplate.asyncSend("test-topic-1", MessageBuilder.withPayload("I'm one way message").build(), new SendCallback() {
            @Override
            public void onSuccess(SendResult sendResult) {

            }

            @Override
            public void onException(Throwable e) {

            }
        });
//        rocketMQTemplate.destroy(); // notes:  once rocketMQTemplate be destroyed, you can not send any message again with this rocketMQTemplate
    }

}