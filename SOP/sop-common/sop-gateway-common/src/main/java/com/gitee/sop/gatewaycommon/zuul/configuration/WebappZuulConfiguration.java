package com.gitee.sop.gatewaycommon.zuul.configuration;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.result.CustomDataNameBuilder;

/**
 * 支持传统webapp开发，没有签名验证
 *
 * @author tanghc
 */
public class WebappZuulConfiguration extends BaseZuulConfiguration {

    static {
        ApiConfig.getInstance().setDataNameBuilder(new CustomDataNameBuilder());
        ApiConfig.getInstance().setShowReturnSign(false);
    }
}
