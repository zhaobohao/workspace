

package com.dc3.center.manager.api;

import com.alibaba.fastjson.JSON;
import com.dc3.api.center.manager.feign.BatchClient;
import com.dc3.center.manager.service.BatchService;
import com.dc3.common.bean.R;
import com.dc3.common.bean.batch.BatchDriver;
import com.dc3.common.constant.Common;
import com.dc3.common.exception.ServiceException;
import com.dc3.common.utils.Dc3Util;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import java.util.List;

/**
 * <p>批量导入 Client 接口实现
 *
 * @author pnoker
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
            return batchService.batchImport(batchDrivers) ? R.ok() : R.fail();
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
            return batchService.batchImport(batchDrivers) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

}
