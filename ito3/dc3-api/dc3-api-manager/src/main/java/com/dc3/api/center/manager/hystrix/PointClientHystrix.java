package com.dc3.api.center.manager.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.PointClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.PointDto;
import com.dc3.common.model.Point;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>PointClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class PointClientHystrix implements FallbackFactory<PointClient> {

    @Override
    public PointClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new PointClient() {

            @Override
            public R<Point> add(Point point) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Point> update(Point point) {
                return R.fail(message);
            }

            @Override
            public R<Point> selectById(Long id) {
                return R.fail(message);
            }

            @Override
            public R<Page<Point>> list(PointDto pointDto) {
                return R.fail(message);
            }

        };
    }
}