package org.springclouddev.iot.data.service.rabbit;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.iot.base.utils.RedisUtil;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.data.entity.PointValue;
import org.springclouddev.iot.data.service.PointValueService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 接收驱动发送过来的数据
 */
@Slf4j
@Component
public class SinglePointValueReceiver {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private PointValueService pointValueService;

    @RabbitHandler
    @RabbitListener(queues = "#{singlePointValueQueue.name}")
    public void pointValueReceive(Channel channel, Message message, PointValue pointValue) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            log.debug("Single point data from {}", message.getMessageProperties().getReceivedRoutingKey());

            if (null == pointValue || null == pointValue.getDeviceId() || null == pointValue.getPointId()) {
                log.error("Invalid single point data: {}", pointValue);
                return;
            }

            threadPoolExecutor.execute(() -> {
                log.debug("Received single point data: {}", pointValue);
                // Save device point data to redis, 15 minutes
                redisUtil.setKey(
                        Common.Cache.REAL_TIME_VALUE_KEY_PREFIX + pointValue.getDeviceId() + "_" + pointValue.getPointId(),
                        pointValue,
                        pointValue.getTimeOut(),
                        pointValue.getTimeUnit()
                );
                // Insert device point data to MongoDB
                // TODO 可根据项目并发情况实现一个定时和批量入库逻辑
                pointValueService.addPointValue(pointValue.setMulti(false));
            });
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
