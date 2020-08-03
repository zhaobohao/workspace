package com.dc3.api.center.manager.hystrix;

import com.dc3.api.center.manager.feign.BatchClient;
import com.dc3.common.bean.R;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

/**
 * <p>ImportClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class BatchClientHystrix implements FallbackFactory<BatchClient> {

    @Override
    public BatchClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new BatchClient() {

            @Override
            public R<Boolean> batchImport(MultipartFile multipartFile) {
                return R.fail(message);
            }

        };
    }
}