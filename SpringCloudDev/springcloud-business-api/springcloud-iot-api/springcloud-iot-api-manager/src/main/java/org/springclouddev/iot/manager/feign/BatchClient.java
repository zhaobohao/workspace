package org.springclouddev.iot.manager.feign;

import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.manager.entity.BatchDriver;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * <p>批量导入 FeignClient
 *

 */
@FeignClient(path = Common.Service.DC3_MANAGER_BATCH_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = BatchClientFallback.class)
public interface BatchClient {

    /**
     * 批量导入 All, 包含：驱动->模版->驱动配置->位号->设备->位号配置
     *
     * @param multipartFile MultipartFile
     * @return Boolean
     */
    @PostMapping("/import")
    R<Boolean> batchImportFile(@RequestParam(value = "file") MultipartFile multipartFile);

    /**
     * 批量导入 All, 包含：驱动->模版->驱动配置->位号->设备->位号配置
     *
     * @param batchDrivers List<BatchDriver>
     * @return Boolean
     */
    @PostMapping("/import/batchDriver")
    R<Boolean> batchImportBatchDriver(@RequestBody List<BatchDriver> batchDrivers);

}
