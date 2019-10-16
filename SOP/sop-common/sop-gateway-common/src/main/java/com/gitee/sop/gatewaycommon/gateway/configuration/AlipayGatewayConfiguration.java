package com.gitee.sop.gatewaycommon.gateway.configuration;

import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.validate.alipay.AlipaySigner;

/**
 * 具备支付宝开放平台能力配置 https://docs.open.alipay.com/api
 *
 * @author tanghc
 */
public class AlipayGatewayConfiguration extends BaseGatewayConfiguration {

    static {
        ApiContext.getApiConfig().setSigner(new AlipaySigner());
    }
}
