package org.springclouddev.iot.manager.feign;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.entity.BatchDriver;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Slf4j
@Component
public class BatchClientFallback implements BatchClient {

    public R<Boolean> batchImportFile(MultipartFile multipartFile) {
        return R.fail("no available file ");
    }

    @Override
    public R<Boolean> batchImportBatchDriver(List<BatchDriver> batchDrivers) {
        return R.fail("no available driver");
    }
}