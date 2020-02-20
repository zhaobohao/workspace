package com.gitee.sop.bridge;

/**
 * 使用Spring Cloud Zuul，推荐使用
 * <p>
 * 注意：下面三个只能使用一个
 */

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.limit.RedisLimitManager;
import com.gitee.sop.gatewaycommon.result.CustomDataNameBuilder;
import com.gitee.sop.gatewaycommon.session.RedisSessionManager;
import com.gitee.sop.gatewaycommon.zuul.configuration.PabZuulConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

/**
 * 开通平安加密算法的开放平台能力。
 */
public class ZuulConfig extends PabZuulConfiguration {
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
 */
//@Configuration
//public class ZuulConfig extends AlipayZuulConfiguration {
//    @Autowired
//    private RedisTemplate redisTemplate;
//
//    @Override
//    protected void doAfter()
//    {
//        super.doAfter();
//        //设置redis分布式限流
//        ApiConfig.getInstance().setLimitManager(new RedisLimitManager(redisTemplate));
//        //设置redis分布式session管理器
//        ApiConfig.getInstance().setSessionManager(new RedisSessionManager(redisTemplate));
////        ApiConfig.getInstance().setDataNameBuilder(new CustomDataNameBuilder("data"));
//    }
//}

/**
 * 开通淘宝开放平台能力
 * @author tanghc
 */
//@Configuration
//public class ZuulConfig extends TaobaoZuulConfiguration {
//  static {
//        new ManagerInitializer();
//        }
//}

/**
 * 对接easyopen
 */
//@Configuration
//public class ZuulConfig extends EasyopenZuulConfiguration {
//    static {
//        Map<String, String> appSecretMap = new HashMap<>();
//        appSecretMap.put("easyopen_test", "G9w0BAQEFAAOCAQ8AMIIBCgKCA");
//        ApiConfig.getInstance().addAppSecret(appSecretMap);
//    }
//
//}
