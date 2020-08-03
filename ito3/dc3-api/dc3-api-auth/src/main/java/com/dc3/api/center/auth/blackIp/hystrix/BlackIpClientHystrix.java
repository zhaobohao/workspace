package com.dc3.api.center.auth.blackIp.hystrix;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.api.center.auth.blackIp.feign.BlackIpClient;
import com.dc3.common.bean.R;
import com.dc3.common.dto.BlackIpDto;
import com.dc3.common.model.BlackIp;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * BlackIpClientHystrix
 *
 * @author pnoker
 */
@Slf4j
@Component
public class BlackIpClientHystrix implements FallbackFactory<BlackIpClient> {

    @Override
    public BlackIpClient create(Throwable throwable) {
        String message = throwable.getMessage() == null ? "No available server for client: DC3-AUTH" : throwable.getMessage();
        log.error("Hystrix:{}", message);

        return new BlackIpClient() {

            @Override
            public R<BlackIp> add(BlackIp blackIp) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> delete(Long id) {
                return R.fail(message);
            }

            @Override
            public R<BlackIp> update(BlackIp blackIp) {
                return R.fail(message);
            }

            @Override
            public R<BlackIp> selectById(Long id) {
                return R.fail(message);
            }

            @Override
            public R<BlackIp> selectByIp(String ip) {
                return R.fail(message);
            }

            @Override
            public R<Page<BlackIp>> list(BlackIpDto blackIpDto) {
                return R.fail(message);
            }

            @Override
            public R<Boolean> checkBlackIpValid(String ip) {
                return R.fail(message);
            }
        };
    }
}