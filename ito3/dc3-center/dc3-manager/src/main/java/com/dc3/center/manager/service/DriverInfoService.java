

package com.dc3.center.manager.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.DriverInfoDto;
import com.dc3.common.model.DriverInfo;

/**
 * <p>DriverInfo Interface
 *
 * @author pnoker
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
