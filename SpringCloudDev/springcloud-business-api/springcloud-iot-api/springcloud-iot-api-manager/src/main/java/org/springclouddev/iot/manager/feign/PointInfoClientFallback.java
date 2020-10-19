package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.PointInfoDto;
import org.springclouddev.iot.manager.entity.PointInfo;
import org.springframework.stereotype.Component;

/**
 * <p>PointInfoClientHystrix
 */
@Slf4j
@Component
public class PointInfoClientFallback implements PointInfoClient {

    String message = "No available server for client: DC3-MANAGER";

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

}