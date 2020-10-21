package org.springclouddev.iot.manager.service;

import org.springclouddev.iot.manager.entity.BatchDriver;

import java.util.List;

/**
 * <p>BatchService Interface
 *

 */
public interface BatchService {

    /**
     * 批量导入 All, 包含：驱动->模版->驱动配置->位号->设备->位号配置
     *
     * @param batchDrivers List<BatchDriver>
     * @return
     */
    Boolean batchImport(List<BatchDriver> batchDrivers);

}
