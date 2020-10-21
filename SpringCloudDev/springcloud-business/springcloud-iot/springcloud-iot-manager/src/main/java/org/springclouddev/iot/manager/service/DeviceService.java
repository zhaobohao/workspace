package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.DeviceDto;
import org.springclouddev.iot.manager.entity.Device;

import java.util.Map;

/**
 * <p>Device Interface
 *

 */
public interface DeviceService extends Service<Device, DeviceDto> {

    /**
     * 根据设备 NAME 和分组 ID 查询
     *
     * @param name    Device Name
     * @param groupId Device Group Id
     * @return Device
     */
    Device selectDeviceByNameAndGroup(String name, Long groupId);

    /**
     * 查询 Device 服务状态
     *
     * @param deviceDto Device Dto
     * @return Map<Long, String>
     */
    Map<Long, String> deviceStatus(DeviceDto deviceDto);
}
