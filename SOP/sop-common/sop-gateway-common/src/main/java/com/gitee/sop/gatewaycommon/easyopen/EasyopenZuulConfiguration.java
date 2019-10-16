package com.gitee.sop.gatewaycommon.easyopen;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import com.gitee.sop.gatewaycommon.zuul.configuration.BaseZuulConfiguration;

/**
 * @author tanghc
 */
public class EasyopenZuulConfiguration extends BaseZuulConfiguration {

    public EasyopenZuulConfiguration() {
        ApiConfig apiConfig = ApiContext.getApiConfig();
        if (compatibilityModel()) {
            ParamNames.APP_KEY_NAME = "app_key";
            ParamNames.API_NAME = "name";
            ParamNames.SIGN_TYPE_NAME = "sign_type";
            ParamNames.APP_AUTH_TOKEN_NAME = "access_token";
            apiConfig.setSigner(new EasyopenSigner());
            apiConfig.setZuulResultExecutor(new EasyopenResultExecutor(false));
            apiConfig.setMergeResult(false);
        } else {
            apiConfig.setZuulResultExecutor(new EasyopenResultExecutor(true));
        }
    }

    /**
     * 是否是兼容模式
     * @return 返回true，返回true可兼容之前的easyopen接口。
     */
    public boolean compatibilityModel() {
        return true;
    }



}
