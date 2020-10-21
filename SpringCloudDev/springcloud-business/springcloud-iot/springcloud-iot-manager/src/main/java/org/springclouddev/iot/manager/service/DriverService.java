package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.DriverDto;
import org.springclouddev.iot.manager.entity.Driver;

import java.util.Map;

/**
 * <p>Driver Interface
 */
public interface DriverService extends Service<Driver, DriverDto> {
    /**
     * 根据驱动 SERVICE NAME 查询
     *
     * @param serviceName Driver Service Name
     * @return Driver
     */
    Driver selectByServiceName(String serviceName);

    /**
     * 根据驱动 HOST & PORT 查询
     *
     * @param host Driver Service Host
     * @param port Driver Service Port
     * @return Driver
     */
    Driver selectByHostPort(String host, Integer port);

    /**
     * 根据 DEVICE ID 查询
     *
     * @param deviceId Device Id
     * @return Driver
     */
    Driver selectByDeviceId(Long deviceId);

    /**
     * 根据 PROFILE ID 查询
     *
     * @param profileId Profile Id
     * @return Driver
     */
    Driver selectByProfileId(Long profileId);

    /**
     * 查询 Driver 服务状态
     *
     * @param driverDto Driver Dto
     * @return Map<String, Boolean>
     */
    Map<String, Boolean> driverStatus(DriverDto driverDto);
}
