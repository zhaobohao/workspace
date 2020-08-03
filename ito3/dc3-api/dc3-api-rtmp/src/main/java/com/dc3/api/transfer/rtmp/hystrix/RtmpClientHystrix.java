package com.dc3.api.transfer.rtmp.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.transfer.rtmp.feign.RtmpClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.RtmpDto;
import com.dc3.common.model.Rtmp;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * RtmpClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class RtmpClientHystrix implements FallbackFactory<RtmpClient> {

    @Override
    public RtmpClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-RTMP" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new RtmpClient() {
            @Override
            public R<Rtmp> add(Rtmp rtmp) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Rtmp> update(Rtmp rtmp) {
                return R.fail(message);
            }

            @Override
            public R<Rtmp> selectById(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Page<Rtmp>> list(RtmpDto rtmpDto) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> start(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> stop(Long id) {
                return R.fail(message);
            }
        };
    }
}