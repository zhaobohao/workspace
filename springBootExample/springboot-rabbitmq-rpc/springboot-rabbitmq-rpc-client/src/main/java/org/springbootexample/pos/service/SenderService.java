package org.springbootexample.pos.service;

import org.springbootexample.pos.model.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springbootexample.pos.config.RabbitConfig;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.amqp.rabbit.AsyncRabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.Future;

/**
 * 消息发送服务
 */
@Service
public class SenderService {
    private Logger logger = LoggerFactory.getLogger(this.getClass());
    @Autowired
    AsyncRabbitTemplate asyncRabbitTemplate;

    @Autowired
    AmqpTemplate amqpTemplate;

    @Async
    public Future<String> sendAsync(User message) {
//        String result = (String) amqpTemplate.convertSendAndReceive(QUEUE_ASYNC_RPC, message, m -> {
//            m.getMessageProperties().setCorrelationIdString(StringUtil.generateUUId());
//            return m;
//        });
        String result = (String) amqpTemplate.convertSendAndReceive(RabbitConfig.QUEUE_ASYNC_RPC, message);
        return new AsyncResult<>(result);
    }

    public Future<String> sendWithFixedReplay(User message) {
//        ListenableFuture<String> future = asyncRabbitTemplate.convertSendAndReceive(QUEUE_ASYNC_RPC_WITH_FIXED_REPLY, message, m -> {
//            m.getMessageProperties().setCorrelationIdString(StringUtil.generateUUId());
//            return m;
//        });
        return asyncRabbitTemplate.convertSendAndReceive(RabbitConfig.QUEUE_ASYNC_RPC_WITH_FIXED_REPLY, message);
    }

}
