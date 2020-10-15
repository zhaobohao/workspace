package com.dc3.center.manager.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.DeviceDto;
import com.dc3.common.model.Device;

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
