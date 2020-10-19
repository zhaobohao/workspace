package org.springclouddev.iot.manager.feign;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.iot.manager.dto.LabelDto;
import org.springclouddev.iot.manager.entity.Label;
import org.springframework.stereotype.Component;

/**
 * <p>LabelClientHystrix
 */
@Slf4j
@Component
public class LabelClientFallback implements LabelClient {

    String message = "No available server for client: DC3-MANAGER";

    @Override
    public R<Label> add(Label label) {
        return R.fail(message);
    }

    @Override
    public R<Boolean> delete(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Label> update(Label label) {
        return R.fail(message);
    }

    @Override
    public R<Label> selectById(Long id) {
        return R.fail(message);
    }

    @Override
    public R<Page<Label>> list(LabelDto labelDto) {
        return R.fail(message);
    }
}