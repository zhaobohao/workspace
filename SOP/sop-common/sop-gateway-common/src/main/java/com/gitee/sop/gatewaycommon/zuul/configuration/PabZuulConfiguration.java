package com.gitee.sop.gatewaycommon.zuul.configuration;

import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.param.PabParameterFormatter;
import com.gitee.sop.gatewaycommon.validate.pab.PabSigner;

/**
 * 具备平安开放平台能力配置
 *
 * @author tanghc
 */
public class PabZuulConfiguration extends BaseZuulConfiguration {

    static {
        ApiContext.getApiConfig().setSigner(new PabSigner());
        ApiContext.getApiConfig().setParameterFormatter(new PabParameterFormatter());
    }
}
