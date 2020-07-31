

package com.dc3.driver.service.impl;

import com.dc3.common.model.Device;
import com.dc3.common.model.Point;
import com.dc3.common.sdk.bean.AttributeInfo;
import com.dc3.common.sdk.service.CustomDriverService;
import com.dc3.driver.service.mqtt.MqttSendHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

import static com.dc3.common.sdk.util.DriverUtils.attribute;

/**
 * @author pnoker
 */
@Slf4j
@Service
public class CustomDriverServiceImpl implements CustomDriverService {

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
    }

}
