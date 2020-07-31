

package com.dc3.api.center.manager.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.DriverInfoClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.DriverInfoDto;
import com.dc3.common.model.DriverInfo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>DriverInfoClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class DriverInfoClientHystrix implements FallbackFactory<DriverInfoClient> {

    @Override
    public DriverInfoClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new DriverInfoClient() {

            @Override
            public R<DriverInfo> add(DriverInfo driverInfo) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(Long id) {
                return R.fail(message);
            }

            @Override
            public R<DriverInfo> update(DriverInfo driverInfo) {
                return R.fail(message);
            }

            @Override
            public R<DriverInfo> selectById(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Page<DriverInfo>> list(DriverInfoDto driverInfoDto) {
                return R.fail(message);
            }

        };
    }
}