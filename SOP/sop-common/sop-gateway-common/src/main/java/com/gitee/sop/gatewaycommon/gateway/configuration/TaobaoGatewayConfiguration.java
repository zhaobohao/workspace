package com.gitee.sop.gatewaycommon.gateway.configuration;

import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import com.gitee.sop.gatewaycommon.validate.taobao.TaobaoSigner;

/**
 * 具备淘宝开放平台能力配置
 * 淘宝开放平台：http://open.taobao.com/doc.htm
 * @author tanghc
 */
public class TaobaoGatewayConfiguration extends BaseGatewayConfiguration {

    static {
        ParamNames.APP_KEY_NAME = "app_key";
        ParamNames.SIGN_TYPE_NAME = "sign_method";
        ParamNames.VERSION_NAME = "v";
        ParamNames.APP_AUTH_TOKEN_NAME = "session";

        ApiContext.getApiConfig().setSigner(new TaobaoSigner());
    }

}
