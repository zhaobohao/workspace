

package com.dc3.common.sdk.service.rabbit;

import cn.hutool.core.convert.Convert;
import com.dc3.api.center.manager.feign.BatchClient;
import com.dc3.common.bean.R;
import com.dc3.common.bean.batch.BatchDriver;
import com.dc3.common.bean.driver.DeviceStatus;
import com.dc3.common.bean.driver.PointValue;
import com.dc3.common.constant.Common;
import com.dc3.common.model.Point;
import com.dc3.common.sdk.bean.DriverContext;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @author pnoker
 */
@Slf4j
@Service
public class DriverService {

    @Value("${spring.application.name}")
    private String serviceName;

    @Resource
    private DriverContext driverContext;
    @Resource
    private RabbitTemplate rabbitTemplate;
    @Resource
    private BatchClient batchClient;

    /**
     * 批量导入
     * 可通过解析配置文件实现
     *
     * @param batchDrivers List<BatchDriver>
     * @return boolean
     */
    public boolean batchImportBatchDriver(List<BatchDriver> batchDrivers) {
        R<Boolean> batchDriver = batchClient.batchImportBatchDriver(batchDrivers);
        return batchDriver.isOk();
    }

    /**
     * 发送设备状态
     * <p>
     * 规范：使用 pointId=0 表示设备状态值
     * Common.Device
     * ONLINE, OFFLINE, MAINTAIN, FAULT
     * 在线，离线，维护，故障
     *
     * @param deviceId Device Id
     * @param status   Common.Device [ONLINE, OFFLINE, MAINTAIN, FAULT]
     */
    public void deviceStatusSender(Long deviceId, String status) {
        DeviceStatus deviceStatus = new DeviceStatus(deviceId, status);
        rabbitTemplate.convertAndSend(Common.Rabbit.TOPIC_EXCHANGE_VALUE, Common.Rabbit.ROUTING_DEVICE_STATUS_PREFIX + serviceName, deviceStatus);
    }

    /**
     * 发送设备状态，同时设置实时数据超时时间
     * <p>
     * 规范：使用 pointId=0 表示设备状态值
     * Common.Device
     * ONLINE, OFFLINE, MAINTAIN, FAULT
     * 在线，离线，维护，故障
     *
     * @param deviceId Device Id
     * @param status   Common.Device [ONLINE, OFFLINE, MAINTAIN, FAULT]
     * @param timeOut  超时时间
     * @param timeUnit 超时时间单位 java.util.concurrent.TimeUnit
     */
    public void deviceStatusSender(Long deviceId, String status, int timeOut, TimeUnit timeUnit) {
        DeviceStatus deviceStatus = new DeviceStatus(deviceId, status);
        deviceStatus.setTimeOut(timeOut).setTimeUnit(timeUnit);
        rabbitTemplate.convertAndSend(Common.Rabbit.TOPIC_EXCHANGE_VALUE, Common.Rabbit.ROUTING_DEVICE_STATUS_PREFIX + serviceName, deviceStatus);
    }

    /**
     * 发送位号值到消息组件，单点存储
     *
     * @param pointValue PointValue
     */
    public void singlePointValueSender(PointValue pointValue) {
        if (null != pointValue) {
            log.debug("Send single point data: {}", pointValue);
            rabbitTemplate.convertAndSend(Common.Rabbit.TOPIC_EXCHANGE_VALUE, Common.Rabbit.ROUTING_SINGLE_VALUE_PREFIX + serviceName, pointValue);
        }
    }

    /**
     * 批量发送位号值到消息组件，单点存储
     *
     * @param pointValues PointValue Array
     */
    public void singlePointValueSender(List<PointValue> pointValues) {
        pointValues.forEach(this::singlePointValueSender);
    }

    /**
     * 发送位号值到消息组件，结构化存储
     *
     * @param pointValue MultiplePointValue
     */
    public void multiPointValueSender(PointValue pointValue) {
        if (null != pointValue) {
            log.debug("Send multiple point data: {}", pointValue);
            rabbitTemplate.convertAndSend(Common.Rabbit.TOPIC_EXCHANGE_VALUE, Common.Rabbit.ROUTING_MULTI_VALUE_PREFIX + serviceName, pointValue);
        }
    }

    /**
     * 批量发送位号值到消息组件，结构化存储
     *
     * @param pointValues PointValue Array
     */
    public void multiPointValueSender(List<PointValue> pointValues) {
        pointValues.forEach(this::multiPointValueSender);
    }

    /**
     * 将位号原始值进行处理和转换
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @param rawValue Raw Value
     * @return PointValue
     */
    public String convertValue(Long deviceId, Long pointId, String rawValue) {
        String value = processValue(rawValue, driverContext.getDevicePoint(deviceId, pointId));
        log.debug("Convert device({}), point({}), raw: {},to value: {}", deviceId, pointId, rawValue, value);
        return value;
    }


    /**
     * process value
     * <p>
     *
     * @param value String Value
     * @param point point.type : string/short/int/double/float/long/boolean
     * @return String Value
     */
    private String processValue(String value, Point point) {
        value = value.trim();
        switch (point.getType()) {
            case Common.ValueType.STRING:
                break;
            case Common.ValueType.BYTE:
            case Common.ValueType.SHORT:
            case Common.ValueType.INT:
            case Common.ValueType.LONG:
            case Common.ValueType.DOUBLE:
            case Common.ValueType.FLOAT:
                try {
                    value = String.format(point.getFormat(),
                            (Convert.convert(Double.class, value) + point.getBase()) * point.getMultiple());
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
                break;
            case Common.ValueType.BOOLEAN:
                try {
                    value = String.valueOf(Boolean.parseBoolean(value));
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
                break;
            default:
                log.error("Invalid device point value type({})", point.getType());
                break;
        }
        return value;
    }
}
