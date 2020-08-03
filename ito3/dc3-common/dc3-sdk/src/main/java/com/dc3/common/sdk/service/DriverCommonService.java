package com.dc3.common.sdk.service;

/**
 * @author pnoker
 */
public interface DriverCommonService {
    /**
     * 初始化 SDK
     */
    void initial();

    /**
     * 向 DeviceDriver 中添加模板
     *
     * @param id Id
     */
    void addProfile(Long id);

    /**
     * 删除 DeviceDriver 中模板
     *
     * @param id Id
     */
    void deleteProfile(Long id);

    /**
     * 向 DeviceDriver 中添加设备
     *
     * @param id Id
     */
    void addDevice(Long id);

    /**
     * 删除 DeviceDriver 中设备
     *
     * @param id Id
     */
    void deleteDevice(Long id);

    /**
     * 更新 DeviceDriver 中设备
     *
     * @param id Id
     */
    void updateDevice(Long id);

    /**
     * 向 DeviceDriver 中添加位号
     *
     * @param id Id
     */
    void addPoint(Long id);

    /**
     * 删除 DeviceDriver 中位号
     *
     * @param id        Id
     * @param profileId Profile Id
     */
    void deletePoint(Long id, Long profileId);

    /**
     * 更新 DeviceDriver 中添加位号
     *
     * @param id Id
     */
    void updatePoint(Long id);

    /**
     * 向 DeviceDriver 中添加驱动配置信息
     *
     * @param id Id
     */
    void addDriverInfo(Long id);

    /**
     * 删除 DeviceDriver 中添加驱动配置信息
     *
     * @param attributeId Attribute Id
     * @param profileId   Profile Id
     */
    void deleteDriverInfo(Long attributeId, Long profileId);

    /**
     * 更新 DeviceDriver 中添加驱动配置信息
     *
     * @param id Id
     */
    void updateDriverInfo(Long id);

    /**
     * 向 DeviceDriver 中添加位号配置信息
     *
     * @param id Id
     */
    void addPointInfo(Long id);

    /**
     * 删除 DeviceDriver 中添加位号配置信息
     *
     * @param pointId     Point Id
     * @param attributeId Attribute Id
     * @param deviceId    Device Id
     */
    void deletePointInfo(Long pointId, Long attributeId, Long deviceId);

    /**
     * 更新 DeviceDriver 中添加位号配置信息
     *
     * @param id Id
     */
    void updatePointInfo(Long id);

}
