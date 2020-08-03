package com.dc3.api.center.manager.feign;

import com.dc3.api.center.manager.hystrix.BatchClientHystrix;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>批量导入 FeignClient
 *
 * @author pnoker
 */
@FeignClient(path = Common.Service.DC3_MANAGER_BATCH_URL_PREFIX, name = Common.Service.DC3_MANAGER_SERVICE_NAME, fallbackFactory = BatchClientHystrix.class)
public interface BatchClient {

    /**
     * 批量导入 All, 包含：驱动->模版->驱动配置->位号->设备->位号配置
     *
     * @param multipartFile MultipartFile
     * @return
     */
    @PostMapping("/import")
    R<Boolean> batchImport(@RequestParam(value = "file") MultipartFile multipartFile);

}
