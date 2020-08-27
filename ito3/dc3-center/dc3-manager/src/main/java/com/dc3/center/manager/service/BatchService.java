

package com.dc3.center.manager.service;

import com.dc3.common.bean.batch.BatchDriver;

import java.util.List;

/**
 * <p>BatchService Interface
 *
 * @author pnoker
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
