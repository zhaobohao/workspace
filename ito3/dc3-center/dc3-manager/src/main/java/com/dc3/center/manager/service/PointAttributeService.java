package com.dc3.center.manager.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.PointAttributeDto;
import com.dc3.common.model.PointAttribute;

/**
 * <p>PointAttribute Interface
 *
 * @author pnoker
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
