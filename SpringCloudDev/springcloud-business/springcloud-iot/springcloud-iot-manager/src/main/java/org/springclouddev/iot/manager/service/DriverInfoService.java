package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.DriverInfoDto;
import org.springclouddev.iot.manager.entity.DriverInfo;

/**
 * <p>DriverInfo Interface
 */
public interface DriverInfoService extends Service<DriverInfo, DriverInfoDto> {
    /**
     * 根据模板属性配置 ID & 模板 ID 查询
     *
     * @param driverAttributeId
     * @param profileId
     * @return
     */
    DriverInfo selectByDriverAttributeId(Long driverAttributeId, Long profileId);
}
