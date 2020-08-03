package com.dc3.center.data.service.rabbit;

import com.dc3.center.data.service.PointValueService;
import com.dc3.common.bean.driver.PointValue;
import com.dc3.common.constant.Common;
import com.dc3.common.utils.RedisUtil;
import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 接收驱动发送过来的数据
 *
 * @author pnoker
 */
@Slf4j
@Component
public class PointValueReceiver {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private PointValueService pointValueService;

    @RabbitHandler
    @RabbitListener(queues = "#{pointValueQueue.name}")
    public void pointValueReceive(Channel channel, Message message, PointValue pointValue) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            log.debug("Point data from {}", message.getMessageProperties().getReceivedRoutingKey());

            if (null == pointValue || null == pointValue.getDeviceId() || null == pointValue.getPointId()) {
                log.error("Invalid data: {}", pointValue);
                return;
            }

        /*
        Convention:
        PointId = 0 indicates device status
        PointId > 0 indicates device point data
         */
            if (pointValue.getPointId().equals(0L)) {
                log.debug("Received device({}) status({})", pointValue.getDeviceId(), pointValue.getRawValue());
                // Save device status to redis, 15 minutes
                redisUtil.setKey(
                        Common.Cache.DEVICE_STATUS_KEY_PREFIX + pointValue.getDeviceId(),
                        pointValue.getRawValue(),
                        15,
                        TimeUnit.MINUTES);
            } else {
                // LinkedBlockingQueue ThreadPoolExecutor
                threadPoolExecutor.execute(() -> {
                    log.debug("Received point data: {}", pointValue);
                    // Save device point data to redis, 15 minutes
                    redisUtil.setKey(
                            Common.Cache.REAL_TIME_VALUE_KEY_PREFIX + pointValue.getDeviceId() + "_" + pointValue.getPointId(),
                            pointValue.getValue(),
                            15,
                            TimeUnit.MINUTES);
                    // Insert device point data to MongoDB
                    pointValueService.add(pointValue);
                });
            }
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
