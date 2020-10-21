package org.springclouddev.iot.data.service.rabbit;

import com.rabbitmq.client.Channel;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.iot.base.utils.RedisUtil;
import org.springclouddev.iot.common.bean.DeviceStatus;
import org.springclouddev.iot.common.constant.Common;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.io.IOException;

/**
 * 接收驱动发送过来的数据
 */
@Slf4j
@Component
public class DeviceStatusReceiver {

    @Resource
    private RedisUtil redisUtil;

    @RabbitHandler
    @RabbitListener(queues = "#{deviceStatusQueue.name}")
    public void pointValueReceive(Channel channel, Message message, DeviceStatus deviceStatus) {
        try {
            channel.basicAck(message.getMessageProperties().getDeliveryTag(), true);
            log.debug("Device status from {}", message.getMessageProperties().getReceivedRoutingKey());

            if (null == deviceStatus || null == deviceStatus.getDeviceId()) {
                log.error("Invalid device status: {}", deviceStatus);
                return;
            }

            log.debug("Received device({}) status({})", deviceStatus.getDeviceId(), deviceStatus.getStatus());
            // Save device status to redis, 15 minutes
            redisUtil.setKey(
                    Common.Cache.DEVICE_STATUS_KEY_PREFIX + deviceStatus.getDeviceId(),
                    deviceStatus.getStatus(),
                    deviceStatus.getTimeOut(),
                    deviceStatus.getTimeUnit()
            );
        } catch (IOException e) {
            log.error(e.getMessage(), e);
        }
    }
}
