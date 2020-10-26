package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.common.base.Service;
import org.springclouddev.iot.manager.dto.PointInfoDto;
import org.springclouddev.iot.manager.entityintInfo;

/**
 * <p>PointInfo Interface
 */
public interface PointInfoService extends Service<PointInfo, PointInfoDto> {
    /**
     * 根据位号配置信息 ID & 设备 ID & 位号 ID 查询
     *
     * @param pointAttributeId
     * @param deviceId
     * @param pointId
     * @return
     */
    PointInfo selectByPointAttributeId(Long pointAttributeId, Long deviceId, Long pointId);
}
