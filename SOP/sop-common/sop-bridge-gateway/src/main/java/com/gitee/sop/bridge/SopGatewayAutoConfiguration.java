package com.gitee.sop.bridge;

import com.gitee.sop.gatewaycommon.config.BaseGatewayAutoConfiguration;
import com.gitee.sop.gatewaycommon.gateway.configuration.AlipayGatewayConfiguration;
import org.springframework.boot.autoconfigure.AutoConfigureBefore;
import org.springframework.cloud.netflix.ribbon.RibbonAutoConfiguration;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * https://blog.csdn.net/seashouwang/article/details/80299571
 * @author tanghc
 */
@Configuration
@Import(GatewayConfig.class)
@AutoConfigureBefore(RibbonAutoConfiguration.class)
public class SopGatewayAutoConfiguration extends BaseGatewayAutoConfiguration {
}
