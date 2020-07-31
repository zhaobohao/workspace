

package com.dc3.api.center.manager.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.PointInfoClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.PointInfoDto;
import com.dc3.common.model.PointInfo;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>PointInfoClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class PointInfoClientHystrix implements FallbackFactory<PointInfoClient> {

    @Override
    public PointInfoClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new PointInfoClient() {

            @Override
            public R<PointInfo> add(PointInfo pointInfo) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(Long id) {
                return R.fail(message);
            }

            @Override
            public R<PointInfo> update(PointInfo pointInfo) {
                return R.fail(message);
            }

            @Override
            public R<PointInfo> selectById(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Page<PointInfo>> list(PointInfoDto pointInfoDto) {
                return R.fail(message);
            }

        };
    }
}