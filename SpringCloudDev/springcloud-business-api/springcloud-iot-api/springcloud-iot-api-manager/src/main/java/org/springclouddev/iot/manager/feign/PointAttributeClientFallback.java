package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.PointAttributeDto;
import org.springclouddev.iot.manager.entityintAttribute;
import org.springframework.stereotype.Component;

/**
 * <p>PointAttributeClientHystrix
 */
@Slf4j
@Component
public class PointAttributeClientFallback implements PointAttributeClient {

    String message = "No available server for client: DC3-MANAGER";

    @Override
    public R<PointAttribute> add(PointAttribute pointAttribute) {
        return R.fail(message);
    }

    @Override
    public R<Boolean> delete(Long id) {
        return R.fail(message);
    }

    @Override
    public R<PointAttribute> update(PointAttribute pointAttribute) {
        return R.fail(message);
    }

    @Override
    public R<PointAttribute> selectById(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Page<PointAttribute>> list(PointAttributeDto pointAttributeDto) {
        return R.fail(message);
    }

}