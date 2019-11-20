package com.gitee.sop.gatewaycommon.param;

import com.alibaba.fastjson.JSONObject;
import com.gitee.sop.gatewaycommon.bean.Isv;
import com.gitee.sop.gatewaycommon.secret.IsvManager;
import com.gitee.sop.gatewaycommon.validate.pab.PabSignature;
import org.springframework.beans.factory.annotation.Autowired;

public class PabParameterFormatter implements ParameterFormatter {
    @Autowired
    private IsvManager isvManager;
    @Override
    public void format(JSONObject requestParams) {
        // 根据appId获取秘钥
        Isv isvInfo = isvManager.getIsv(requestParams.getString(requestParams.getString(ParamNames.APP_KEY_NAME)));
        String cusPublicKey = isvInfo.getSecretInfo();
        String pabPrivateKey=isvInfo.getPrivateKeyPlatform();
        String signType= requestParams.getString(ParamNames.ENCRYPTION_TYPE_NAME);
        //解密业务数据包，并重新赋值
        requestParams.put(ParamNames.BIZ_CONTENT_NAME,PabSignature.checkSignAndDecryptBySignType(requestParams,cusPublicKey,pabPrivateKey,true,true,signType));
    }
}
