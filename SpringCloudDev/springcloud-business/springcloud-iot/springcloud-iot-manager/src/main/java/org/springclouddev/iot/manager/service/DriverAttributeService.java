package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.DriverAttributeDto;
import org.springclouddev.iot.manager.entity.DriverAttribute;

/**
 * <p>DriverAttribute Interface
 */
public interface DriverAttributeService extends Service<DriverAttribute, DriverAttributeDto> {
    /**
     * 根据驱动配置属性 NAME 查询
     *
     * @param name
     * @param driverId
     * @return
     */
    DriverAttribute selectByNameAndDriverId(String name, Long driverId);
}
