package org.springclouddev.iot.common.sdk.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.log.exception.ServiceException;
import org.springclouddev.iot.common.sdk.bean.AttributeInfo;
import org.springclouddev.iot.common.sdk.bean.DriverContext;
import org.springclouddev.iot.common.sdk.service.CustomDriverService;
import org.springclouddev.iot.common.sdk.service.DriverCommandService;
import org.springclouddev.iot.common.sdk.service.rabbit.DriverService;
import org.springclouddev.iot.data.entityintValue;
import org.springclouddev.iot.manager.entity.Device;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;


@Slf4j
@Service
public class DriverCommandServiceImpl implements DriverCommandService {

    @Resource
    private DriverContext driverContext;
    @Resource
    private DriverService driverService;
    @Resource
    private CustomDriverService customDriverService;

    @Override
    public PointValue read(Long deviceId, Long pointId) {
        Device device = driverContext.getDevice(deviceId);
        try {
            String rawValue = customDriverService.read(
                    driverContext.getProfileDriverInfo(device.getProfileId()),
                    driverContext.getDevicePointInfo(deviceId, pointId),
                    device,
                    driverContext.getDevicePoint(deviceId, pointId)
            );

            PointValue pointValue = new PointValue(deviceId, pointId, rawValue, driverService.convertValue(deviceId, pointId, rawValue));
            driverService.singlePointValueSender(pointValue);
            return pointValue;
        } catch (Exception e) {
            log.error("DriverCommandServiceImpl.read{}", e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

    @Override
    public Boolean write(Long deviceId, Long pointId, String value) {
        Device device = driverContext.getDevice(deviceId);
        try {
            return customDriverService.write(
                    driverContext.getProfileDriverInfo(device.getProfileId()),
                    driverContext.getDevicePointInfo(deviceId, pointId),
                    device,
                    new AttributeInfo(value, driverContext.getDevicePoint(deviceId, pointId).getType())
            );
        } catch (Exception e) {
            log.error("DriverCommandServiceImpl.write{}", e.getMessage(), e);
            throw new ServiceException(e.getMessage());
        }
    }

}
