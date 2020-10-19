package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.DriverAttributeDto;
import org.springclouddev.iot.manager.entity.DriverAttribute;
import org.springframework.stereotype.Component;

/**
 * <p>DriverAttributeClient
 *

 */
@Slf4j
@Component
public class DriverAttributeClientFallback implements DriverAttributeClient {

        String message = "No available server for client: DC3-MANAGER" ;
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

}