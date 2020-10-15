package com.dc3.center.manager.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.DriverAttributeDto;
import com.dc3.common.model.DriverAttribute;

/**
 * <p>DriverAttribute Interface
 *

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
