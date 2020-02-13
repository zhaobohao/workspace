package com.mallplus.common.feign;

import com.mallplus.common.config.FeignConfiguration;
import com.mallplus.common.constant.ServiceNameConstants;
import com.mallplus.common.entity.ums.UmsMember;
import com.mallplus.common.feign.fallback.UaaServiceFallbackFactory;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.RequestMapping;

@FeignClient(name = ServiceNameConstants.UAA_SERVICE, fallbackFactory = UaaServiceFallbackFactory.class, decode404 = true,configuration = FeignConfiguration.class)
public interface UaaFeignClient {
    @RequestMapping(value = { "/oauth/ums/member" }, produces = "application/json") // 获取用户。
    public UmsMember getCurrentMember() ;
}
