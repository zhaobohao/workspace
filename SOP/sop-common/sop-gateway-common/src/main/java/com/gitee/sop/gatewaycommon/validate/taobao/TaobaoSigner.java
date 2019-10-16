package com.gitee.sop.gatewaycommon.validate.taobao;


import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.validate.AbstractSigner;
import com.gitee.sop.gatewaycommon.validate.SignConfig;
import com.gitee.sop.gatewaycommon.validate.SignEncipher;
import com.gitee.sop.gatewaycommon.validate.SignEncipherHMAC_MD5;
import com.gitee.sop.gatewaycommon.validate.SignEncipherMD5;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * 淘宝开放平台签名验证实现，http://open.taobao.com/doc.htm?docId=101617&docType=1
 *
 * @author tanghc
 */
public class TaobaoSigner extends AbstractSigner {

    private Map<String, SignEncipher> signEncipherMap = new HashMap<>();

    public TaobaoSigner() {
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
            String val = SignConfig.wrapVal(param.get(paramName));
            paramNameValue.append(paramName).append(val);
        }

        // 第三步：使用MD5/HMAC加密
        String source = paramNameValue.toString();
        byte[] bytes = signEncipher.encrypt(source, secret);

        // 第四步：把二进制转化为大写的十六进制
        return byte2hex(bytes).toUpperCase();
    }
}
