package com.gitee.sop.gateway.config;

import com.gitee.sop.gatewaycommon.gateway.configuration.AlipayGatewayConfiguration;


/**
 * 使用Spring Cloud Gateway
 *
 * 注意：下面两个只能使用一个。
 *
 * 使用前，前往启动类SopGatewayApplication.java 注释掉@EnableZuulProxy
 */

/**
 * 开通支付宝开放平台能力
 * @author tanghc
 */

//@Configuration
public class GatewayConfig extends AlipayGatewayConfiguration {

}

/**
 * 开通淘宝开放平能力
 */
//@Configuration
//public class GatewayConfig extends TaobaoGatewayConfiguration {
//
//    {
//        Map<String, String> appSecretStore = new HashMap();
//        appSecretStore.put("taobao_test", "G9w0BAQEFAAOCAQ8AMIIBCgKCA");
//        ApiContext.getApiConfig().addAppSecret(appSecretStore);
//    }
//}

