package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.PointDto;
import org.springclouddev.iot.manager.entity.Point;
import org.springframework.stereotype.Component;

/**
 * <p>PointClientHystrix
 */
@Slf4j
@Component
public class PointClientFallback implements PointClient {

    String message = "No available server for client: DC3-MANAGER";

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

}