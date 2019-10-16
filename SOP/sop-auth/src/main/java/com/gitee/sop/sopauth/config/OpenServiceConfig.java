package com.gitee.sop.sopauth.config;

import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.configuration.AlipayServiceConfiguration;
import org.springframework.context.annotation.Configuration;

/**
 * 使用支付宝开放平台功能
 *
 * @author tanghc
 */
@Configuration
public class OpenServiceConfig extends AlipayServiceConfiguration {


    static {
        ServiceConfig.getInstance().getI18nModules().add("i18n/isp/goods_error");
    }

}
