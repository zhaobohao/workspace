package com.dc3.api.center.manager.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.DeviceClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.DeviceDto;
import com.dc3.common.model.Device;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * <p>DeviceClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class DeviceClientHystrix implements FallbackFactory<DeviceClient> {

    @Override
    public DeviceClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new DeviceClient() {

            @Override
            public R<Device> add(Device device) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Device> update(Device device) {
                return R.fail(message);
            }

            @Override
            public R<Device> selectById(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Map<Long, String>> deviceStatus(DeviceDto deviceDto) {
                return R.fail(message);
            }

            @Override
            public R<Page<Device>> list(DeviceDto deviceDto) {
                return R.fail(message);
            }

        };
    }
}