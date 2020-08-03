package com.dc3.api.center.manager.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.DriverAttributeClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.DriverAttributeDto;
import com.dc3.common.model.DriverAttribute;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>DriverAttributeClient
 *
 * @author pnoker
 */
@Slf4j
@Component
public class DriverAttributeClientHystrix implements FallbackFactory<DriverAttributeClient> {

    @Override
    public DriverAttributeClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new DriverAttributeClient() {

            @Override
            public R<DriverAttribute> add(DriverAttribute driverAttribute) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(Long id) {
                return R.fail(message);
            }

            @Override
            public R<DriverAttribute> update(DriverAttribute driverAttribute) {
                return R.fail(message);
            }

            @Override
            public R<DriverAttribute> selectById(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Page<DriverAttribute>> list(DriverAttributeDto driverAttributeDto) {
                return R.fail(message);
            }

        };
    }
}