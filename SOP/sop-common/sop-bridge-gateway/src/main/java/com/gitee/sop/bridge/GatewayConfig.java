package com.gitee.sop.bridge;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.gateway.configuration.AlipayGatewayConfiguration;
import com.gitee.sop.gatewaycommon.limit.RedisLimitManager;
import com.gitee.sop.gatewaycommon.result.CustomDataNameBuilder;
import com.gitee.sop.gatewaycommon.session.RedisSessionManager;
import com.gitee.sop.gatewaycommon.zuul.configuration.PabZuulConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;


/**
 * 使用Spring Cloud Gateway
 *
 * 注意：下面两个只能使用一个。
 *
 * 使用前，前往启动类SopGatewayApplication.java 注释掉@EnableZuulProxy
 */
public class GatewayConfig extends PabZuulConfiguration {
@Autowired
private RedisTemplate redisTemplate;

@Override
protected void doAfter()
        {
        super.doAfter();
        //设置redis分布式限流
        ApiConfig.getInstance().setLimitManager(new RedisLimitManager(redisTemplate));
        //设置redis分布式session管理器
        ApiConfig.getInstance().setSessionManager(new RedisSessionManager(redisTemplate));
        ApiConfig.getInstance().setDataNameBuilder(new CustomDataNameBuilder("data"));
        }
        }
/**
 * 开通支付宝开放平台能力
 * @author tanghc


public class GatewayConfig extends AlipayGatewayConfiguration {

}
 */
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

