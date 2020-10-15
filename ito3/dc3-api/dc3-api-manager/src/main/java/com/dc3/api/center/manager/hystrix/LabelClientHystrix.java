package com.dc3.api.center.manager.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.manager.feign.LabelClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.LabelDto;
import com.dc3.common.model.Label;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * <p>LabelClientHystrix
 *

 */
@Slf4j
@Component
public class LabelClientHystrix implements FallbackFactory<LabelClient> {

    @Override
    public LabelClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-MANAGER" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new LabelClient() {

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
        };
    }
}