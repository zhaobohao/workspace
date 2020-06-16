package org.apache.rocketmq.spring.starter.annotation;

import lombok.Getter;
import lombok.Setter;
import org.apache.rocketmq.common.filter.ExpressionType;
import org.apache.rocketmq.common.protocol.heartbeat.MessageModel;
import org.apache.rocketmq.spring.starter.enums.ConsumeMode;
import org.apache.rocketmq.spring.starter.enums.SelectorType;
import org.springframework.stereotype.Service;

import java.lang.annotation.*;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface RocketMQMessageListener {

    /**
     * used for connecting different MQ cluster.
     */
    String nameServer() default "";

    String instanceName() default "";

    /**
     * 如果为True,要使用消息轨迹，在broke服务启动时，加traceTopicEnable=true
     * @return
     */
    String traceTopicEnable() default "false";

    /**
     * Consumers of the same role is required to have exactly same subscriptions and consumerGroup to correctly achieve
     * load balance. It's required and needs to be globally unique.
     * </p>
     * <p>
     * See <a href="http://rocketmq.apache.org/docs/core-concept/">here</a> for further discussion.
     */
    String consumerGroup();

    /**
     * Topic name
     */
    String topic();

    /**
     * Control how to selector message
     *
     * @see ExpressionType
     */
    SelectorType selectorType() default SelectorType.TAG;

    /**
     * Control which message can be select. Grammar please see {@link ExpressionType#TAG} and {@link ExpressionType#SQL92}
     */
    String selectorExpress() default "*";

    /**
     * Control consume mode, you can choice receive message concurrently or orderly
     */
    ConsumeMode consumeMode() default ConsumeMode.CONCURRENTLY;

    /**
     * Control message mode, if you want all subscribers receive message all message, broadcasting is a good choice.
     */
    MessageModel messageModel() default MessageModel.CLUSTERING;

    /**
     * Max consumer thread number
     */
    int consumeThreadMax() default 64;

    /**
     * Re-consume times for concurrent/orderly message
     */
    int reconsumeTimes() default 0;

}
