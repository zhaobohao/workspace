package com.dc3.common.sdk.service.impl;

import com.dc3.common.bean.driver.PointValue;
import com.dc3.common.model.Device;
import com.dc3.common.sdk.bean.AttributeInfo;
import com.dc3.common.sdk.bean.DriverContext;
import com.dc3.common.sdk.service.CustomDriverService;
import com.dc3.common.sdk.service.DriverCommandService;
import com.dc3.common.sdk.service.rabbit.DriverService;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author pnoker
 */
@Slf4j
@Service
public class DriverCommandServiceImpl implements DriverCommandService {
    @Resource
    private DriverContext driverContext;
    @Resource
    private CustomDriverService customDriverService;
    @Resource
    private DriverService driverService;

    @Override
    @SneakyThrows
    public PointValue read(Long deviceId, Long pointId) {
        Device device = driverContext.getDevice(deviceId);
        String rawValue = customDriverService.read(
                driverContext.getProfileDriverInfo(device.getProfileId()),
                driverContext.getDevicePointInfo(deviceId, pointId),
                device,
                driverContext.getDevicePoint(deviceId, pointId));

        PointValue pointValue = driverService.convertValue(deviceId, pointId, rawValue);
        driverService.pointValueSender(pointValue);
        return pointValue;
    }

    @Override
    @SneakyThrows
    public Boolean write(Long deviceId, Long pointId, String value) {
        Device device = driverContext.getDevice(deviceId);
        return customDriverService.write(
                driverContext.getProfileDriverInfo(device.getProfileId()),
                driverContext.getDevicePointInfo(deviceId, pointId),
                device,
                new AttributeInfo(value, driverContext.getDevicePoint(deviceId, pointId).getType())
        );
    }

}
