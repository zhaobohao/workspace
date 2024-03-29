package org.springclouddev.iot.driver.service.mqtt;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.iot.common.sdk.service.rabbit.DriverService;
import org.springclouddev.iot.data.entityintValue;
import org.springclouddev.iot.driver.bean.DevicePayLoad;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.integration.annotation.ServiceActivator;
import org.springframework.messaging.MessageHandler;

import javax.annotation.Resource;

/**
 * mqtt上行数据例子：
 * {
 * deviceId:1,
 * pointId:1,
 * value:"1212"
 * }
 * 可根据实际情况定义 DevicePayLoad 类属性
 */
@Slf4j
@Configuration
public class MqttReceiveHandler {
    @Resource
    private DriverService driverService;

    /**
     * 说明：
     * ConditionalOnProperty(value = "driver.mqtt.default.receive.enable")
     * 根据配置属性driver.mqtt.default.receive.enable选择是否开启 Default Topic 主题的数据接收逻辑
     *
     * @return
     */
    @Bean
    @ServiceActivator(inputChannel = "defaultMqttInputChannel")
    @ConditionalOnProperty(value = "driver.mqtt.default.receive.enable")
    public MessageHandler defaultHandler() {
        return message -> {
            log.info(
                    "defaultTopicReceiver\nheader:{},\npayload:{}",
                    JSON.toJSONString(message.getHeaders(), true),
                    JSON.toJSONString(message.getPayload(), true)
            );
        };
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttInputChannel")
    public MessageHandler handler() {
        return message -> {
            log.info(
                    "{}\nheader:{}, payload:{}",
                    message.getHeaders().get("mqtt_receivedTopic"),
                    JSON.toJSONString(message.getHeaders(), true),
                    JSON.toJSONString(message.getPayload(), true)
            );
            DevicePayLoad devicePayLoad = JSON.parseObject(message.getPayload().toString(), DevicePayLoad.class);
            PointValue pointValue = new PointValue(devicePayLoad.getDeviceId(), devicePayLoad.getPointId(), devicePayLoad.getValue(),
                    driverService.convertValue(devicePayLoad.getDeviceId(), devicePayLoad.getPointId(), devicePayLoad.getValue()));
            driverService.singlePointValueSender(pointValue);
        };
    }
}
