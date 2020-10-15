package com.dc3.api.center.data.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.data.feign.PointValueClient;
import com.dc3.common.bean.R;
import com.dc3.common.bean.driver.PointValue;
import com.dc3.common.bean.driver.PointValueDto;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.util.List;

/**
 * PointValueClientHystrix
 *

 */
@Slf4j
@Component
public class PointValueClientHystrix implements FallbackFactory<PointValueClient> {

    @Override
    public PointValueClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-DATA" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new PointValueClient() {

            @Override
            public R<String> status(Long deviceId) {
                return R.fail(message);
            }

            @Override
            public R<List<PointValue>> realtime(@NotNull Long deviceId) {
                return R.fail(message);
            }

            @Override
            public R<PointValue> realtime(Long deviceId, Long pointId) {
                return R.fail(message);
            }

            @Override
            public R<PointValue> latest(@NotNull Long deviceId) {
                return R.fail(message);
            }

            @Override
            public R<PointValue> latest(Long deviceId, Long pointId) {
                return R.fail(message);
            }

            @Override
            public R<Page<PointValue>> list(PointValueDto pointValueDto) {
                return R.fail(message);
            }

        };
    }
}