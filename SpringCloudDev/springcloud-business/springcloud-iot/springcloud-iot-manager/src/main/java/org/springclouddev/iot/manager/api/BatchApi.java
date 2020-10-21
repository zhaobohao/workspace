package org.springclouddev.iot.manager.api;

import com.alibaba.fastjson.JSON;
import org.springclouddev.core.log.exception.ServiceException;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.entity.BatchDriver;
import org.springclouddev.iot.manager.service.BatchService;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.common.utils.Dc3Util;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.iot.manager.feign.BatchClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>批量导入 Client 接口实现
 *

 */
@Slf4j
@RestController
@RequestMapping(Common.Service.DC3_MANAGER_BATCH_URL_PREFIX)
public class BatchApi implements BatchClient {
    @Resource
    private BatchService batchService;


    @Override
    public R<Boolean> batchImportFile(MultipartFile multipartFile) {
        try {
            if (multipartFile.isEmpty()) {
                throw new ServiceException("Import file is empty");
            }
            // Convert json file to ImportAll object
            List<BatchDriver> batchDrivers = JSON.parseArray(
                    Dc3Util.inputStreamToString(multipartFile.getInputStream()),
                    BatchDriver.class
            );
            if (null == batchDrivers) {
                throw new ServiceException("Import file is blank");
            }
            return batchService.batchImport(batchDrivers) ? R.success("ok") : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

    @Override
    public R<Boolean> batchImportBatchDriver(List<BatchDriver> batchDrivers) {
        try {
            if (null == batchDrivers) {
                throw new ServiceException("Import file is blank");
            }
            return batchService.batchImport(batchDrivers) ? R.success("ok") : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

}
