package com.gitee.sop.gatewaycommon.config;

import com.alibaba.fastjson.JSON;
import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import javax.annotation.PostConstruct;

/**
 * @author tanghc
 */
@Slf4j
@EnableConfigurationProperties(ApiConfigProperties.class)
public class BaseGatewayAutoConfiguration {

    @Autowired
    private ApiConfigProperties apiConfigProperties;

    @PostConstruct
    public void after() {
        log.info("网关基本配置：{}", JSON.toJSONString(apiConfigProperties));
        ApiConfig apiConfig = ApiConfig.getInstance();
        BeanUtils.copyProperties(apiConfigProperties, apiConfig);
    }

}
