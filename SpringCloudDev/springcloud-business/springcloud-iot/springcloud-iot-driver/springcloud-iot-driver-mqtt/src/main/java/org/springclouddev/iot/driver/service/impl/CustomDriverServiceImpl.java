package org.springclouddev.iot.driver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.sdk.bean.AttributeInfo;
import org.springclouddev.iot.common.sdk.bean.DriverContext;
import org.springclouddev.iot.common.sdk.service.CustomDriverService;
import org.springclouddev.iot.common.sdk.service.rabbit.DriverService;
import org.springclouddev.iot.driver.service.mqtt.MqttSendHandler;
import org.springclouddev.iot.manager.entity.Device;
import org.springclouddev.iot.manager.entity.Point;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static org.springclouddev.iot.common.sdk.util.DriverUtils.attribute;


@Slf4j
@Service
public class CustomDriverServiceImpl implements CustomDriverService {

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverService driverService;
    @Resource
    private MqttSendHandler mqttSendHandler;

    @Override
    public void initial() {
    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception {
        return "nil";
    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo values) throws Exception {
        String commandTopic = attribute(pointInfo, "commandTopic"), value = values.getValue();
        try {
            int commandQos = attribute(pointInfo, "commandQos");
            mqttSendHandler.sendToMqtt(commandTopic, commandQos, value);
        } catch (Exception e) {
            mqttSendHandler.sendToMqtt(commandTopic, value);
        }
        return true;
    }

    @Override
    public void schedule() {

        /*
        TODO:设备状态
        上传设备状态，可自行灵活拓展，不一定非要在schedule()接口中实现，也可以在read中实现设备状态的设置；
        你可以通过某种判断机制确定设备的状态，然后通过driverService.deviceStatusSender(deviceId,DeviceStatus)接口将设备状态交给SDK管理。

        设备状态（DeviceStatus）如下：
        ONLINE:在线
        OFFLINE:离线
        MAINTAIN:维护
        FAULT:故障
         */
        driverContext.getDeviceMap().keySet().forEach(id -> driverService.deviceStatusSender(id, Common.Device.ONLINE));
    }

}
