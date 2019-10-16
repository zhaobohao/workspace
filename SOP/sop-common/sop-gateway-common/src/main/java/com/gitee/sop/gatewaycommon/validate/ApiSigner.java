package com.gitee.sop.gatewaycommon.validate;


import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 签名验证实现
 *
 * @author tanghc
 */
public class ApiSigner extends AbstractSigner {

    private Map<String, SignEncipher> signEncipherMap = new HashMap<>();

    public ApiSigner() {
        signEncipherMap.put("md5", new SignEncipherMD5());
        signEncipherMap.put("hmac", new SignEncipherHMAC_MD5());
    }


    @Override
    public String buildServerSign(ApiParam param, String secret) {
        String signMethod = param.fetchSignMethod();
        SignEncipher signEncipher = signEncipherMap.get(signMethod);
        if (signEncipher == null) {
            throw ErrorEnum.ISV_INVALID_SIGNATURE_TYPE.getErrorMeta().getException(signMethod);
        }

        // 第一步：参数排序
        Set<String> keySet = param.keySet();
        List<String> paramNames = new ArrayList<>(keySet);
        Collections.sort(paramNames);

        // 第二步：把所有参数名和参数值串在一起
        StringBuilder paramNameValue = new StringBuilder();
        for (String paramName : paramNames) {
            paramNameValue.append(paramName).append(param.get(paramName));
        }

        // 第三步：使用MD5/HMAC加密
        String source = paramNameValue.toString();
        byte[] bytes = signEncipher.encrypt(source, secret);

        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes).toUpperCase();
    }
}
