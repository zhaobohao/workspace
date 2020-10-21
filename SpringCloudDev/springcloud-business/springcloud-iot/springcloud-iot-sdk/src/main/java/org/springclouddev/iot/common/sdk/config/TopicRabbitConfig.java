package org.springclouddev.iot.common.sdk.config;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.iot.common.constant.Common;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.RabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Slf4j
@Configuration
public class TopicRabbitConfig {
    @Value("${spring.application.name}")
    private String serviceName;

    @Bean
    RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(new Jackson2JsonMessageConverter());
        rabbitTemplate.setMandatory(true);
        rabbitTemplate.setReturnCallback((message, replyCode, replyText, exchange, routingKey) -> {
            log.error("Send message({}) to exchange({}), routingKey({}) failed: {}", message, exchange, routingKey, replyText);
        });
        rabbitTemplate.setConfirmCallback((correlationData, ack, cause) -> {
            if (!ack) {
                log.error("CorrelationData({}) ack failed: {}", correlationData, cause);
            }
        });
        return rabbitTemplate;
    }

    @Bean
    TopicExchange notifyExchange() {
        return new TopicExchange(Common.Rabbit.TOPIC_EXCHANGE_NOTIFY, true, true);
    }

    @Bean
    Queue driverNotifyQueue() {
        return new Queue(Common.Rabbit.QUEUE_DRIVER_NOTIFY_PREFIX + this.serviceName, false, false, true);
    }

    @Bean
    Binding driverNotifyBinding() {
        return BindingBuilder
                .bind(driverNotifyQueue())
                .to(notifyExchange())
                .with(Common.Rabbit.ROUTING_DEVICE_NOTIFY_PREFIX + this.serviceName);
    }

    @Bean
    TopicExchange valueExchange() {
        return new TopicExchange(Common.Rabbit.TOPIC_EXCHANGE_VALUE, true, true);
    }

    @Bean
    Queue deviceStatusQueue() {
        return new Queue(Common.Rabbit.QUEUE_DEVICE_STATUS, true, false, true);
    }

    @Bean
    Queue singlePointValueQueue() {
        return new Queue(Common.Rabbit.QUEUE_POINT_SINGLE_VALUE, true, false, true);
    }

    @Bean
    Queue multiplePointValueQueue() {
        return new Queue(Common.Rabbit.QUEUE_POINT_MULTI_VALUE, true, false, true);
    }

    @Bean
    public RabbitListenerContainerFactory<?> rabbitListenerContainerFactory(ConnectionFactory connectionFactory) {
        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(new Jackson2JsonMessageConverter());
        factory.setAcknowledgeMode(AcknowledgeMode.MANUAL);
        return factory;
    }

}
