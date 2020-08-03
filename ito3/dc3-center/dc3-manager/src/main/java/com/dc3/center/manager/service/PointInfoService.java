package com.dc3.center.manager.service;

import com.dc3.common.base.Service;
import com.dc3.common.dto.PointInfoDto;
import com.dc3.common.model.PointInfo;

/**
 * <p>PointInfo Interface
 *
 * @author pnoker
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
