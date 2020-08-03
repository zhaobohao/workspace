package com.dc3.center.manager.api;

import com.dc3.api.center.manager.feign.BatchClient;
import com.dc3.center.manager.service.BatchService;
import com.dc3.common.bean.R;
import com.dc3.common.constant.Common;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;

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
    public R<Boolean> batchImport(MultipartFile multipartFile) {
        try {
            return batchService.batchImport(multipartFile) ? R.ok() : R.fail();
        } catch (Exception e) {
            return R.fail(e.getMessage());
        }
    }

}
