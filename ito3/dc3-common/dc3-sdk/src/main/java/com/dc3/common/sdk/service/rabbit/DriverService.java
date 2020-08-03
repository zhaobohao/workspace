package com.dc3.common.sdk.service.rabbit;

import cn.hutool.core.convert.Convert;
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

    /**
     * 发送位号值到消息组件
     *
     * @param pointValue PointValue
     */
    public void pointValueSender(PointValue pointValue) {
        if (null != pointValue) {
            log.debug("Send point data: {}", pointValue);
            rabbitTemplate.convertAndSend(Common.Rabbit.TOPIC_EXCHANGE_VALUE, Common.Rabbit.ROUTING_KEY_PREFIX + serviceName, pointValue);
        }
    }

    /**
     * 批量发送位号值到消息组件
     *
     * @param pointValues PointValue Array
     */
    public void pointValueSender(List<PointValue> pointValues) {
        pointValues.forEach(this::pointValueSender);
    }

    /**
     * 发送设备状态
     * <p>
     * 规范：使用 pointId=0 表示设备状态值
     * Common.Device
     * ONLINE, OFFLINE, MAINTAIN, FAULT
     * 在线，离线，维护，故障
     *
     * @param deviceId     Device Id
     * @param deviceStatus Common.Device [ONLINE, OFFLINE, MAINTAIN, FAULT]
     */
    public void deviceStatusSender(Long deviceId, String deviceStatus) {
        PointValue pointValue = new PointValue(deviceId, 0L, deviceStatus, null);
        pointValueSender(pointValue);
    }

    /**
     * 将位号原始值进行处理和转换
     *
     * @param deviceId Device Id
     * @param pointId  Point Id
     * @param rawValue Raw Value
     * @return PointValue
     */
    public PointValue convertValue(Long deviceId, Long pointId, String rawValue) {
        PointValue value = new PointValue(
                deviceId,
                pointId,
                rawValue,
                processValue(rawValue, driverContext.getDevicePoint(deviceId, pointId))
        );
        log.debug("Convert device({}), point({}), raw: {},to value: {}", deviceId, pointId, value.getRawValue(), value.getValue());
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
            case Common.ValueType.SHORT:
            case Common.ValueType.INT:
            case Common.ValueType.LONG:
                try {
                    value = String.format("%.0f",
                            (Convert.convert(Double.class, value) + point.getBase()) * point.getMultiple());
                } catch (Exception e) {
                    log.warn(e.getMessage());
                }
                break;
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
