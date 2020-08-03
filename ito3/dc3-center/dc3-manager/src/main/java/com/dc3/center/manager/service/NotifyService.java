package com.dc3.center.manager.service;

import com.dc3.common.model.Driver;

/**
 * <p>Notify Interface
 *
 * @author pnoker
 */
public interface NotifyService {

    /**
     * 通知驱动 新增模板(ADD) / 删除模板(DELETE)
     *
     * @param driver        driver
     * @param profileId     Profile Id
     * @param operationType Operation Type
     */
    void notifyDriverProfile(Driver driver, Long profileId, String operationType);

    /**
     * 通知驱动 新增设备(ADD) / 删除设备(DELETE) / 修改设备(UPDATE)
     *
     * @param deviceId      Device Id
     * @param profileId     Profile Id
     * @param operationType Operation Type
     */
    void notifyDriverDevice(Long deviceId, Long profileId, String operationType);

    /**
     * 通知驱动 新增位号(ADD) / 删除位号(DELETE) / 修改位号(UPDATE)
     *
     * @param pointId       Point Id
     * @param profileId     Profile Id
     * @param operationType Operation Type
     */
    void notifyDriverPoint(Long pointId, Long profileId, String operationType);

    /**
     * 通知驱动 新增驱动配置(ADD) / 删除驱动配置(DELETE) / 更新驱动配置(UPDATE)
     *
     * @param driverInfoId  Driver Info Id
     * @param attributeId   Attribute Id
     * @param profileId     Profile Id
     * @param operationType Operation Type
     */
    void notifyDriverDriverInfo(Long driverInfoId, Long attributeId, Long profileId, String operationType);

    /**
     * 通知驱动 新增位号配置(ADD) / 删除位号配置(DELETE) / 更新位号配置(UPDATE)
     *
     * @param pointInfoId   Point Id
     * @param attributeId   Attribute Id
     * @param deviceId      Device Id
     * @param operationType Operation Type
     */
    void notifyDriverPointInfo(Long pointInfoId, Long attributeId, Long deviceId, String operationType);

}
