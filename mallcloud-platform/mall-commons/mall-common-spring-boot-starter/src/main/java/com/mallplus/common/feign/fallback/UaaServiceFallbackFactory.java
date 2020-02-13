package com.mallplus.common.feign.fallback;

import com.mallplus.common.entity.ums.UmsMember;
import com.mallplus.common.feign.UaaFeignClient;
import com.mallplus.common.utils.CommonResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * UaaService 降级使用
 */
@Slf4j
@Component
public class UaaServiceFallbackFactory implements FallbackFactory<UaaFeignClient> {
    @Override
    public UaaFeignClient create(Throwable throwable) {
        return new UaaFeignClient() {
            @Override
            public UmsMember getCurrentMember() {
                return new UmsMember();
            }
        };
    }
}
