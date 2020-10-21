package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.PointAttributeDto;
import org.springclouddev.iot.manager.entity.PointAttribute;

/**
 * <p>PointAttribute Interface
 */
public interface PointAttributeService extends Service<PointAttribute, PointAttributeDto> {
    /**
     * 根据位号配置属性 NAME 查询
     *
     * @param name
     * @param driverId
     * @return
     */
    PointAttribute selectByNameAndDriverId(String name, Long driverId);
}
