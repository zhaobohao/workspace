package com.gitee.sop.gatewaycommon.easyopen;

import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.validate.AbstractSigner;
import org.apache.commons.codec.digest.DigestUtils;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Set;

/**
 * @author tanghc
 */
public class EasyopenSigner extends AbstractSigner {
    @Override
    protected String buildServerSign(ApiParam params, String secret) {
        Set<String> keySet = params.keySet();
        List<String> paramNames = new ArrayList<>(keySet);

        Collections.sort(paramNames);

        StringBuilder paramNameValue = new StringBuilder();

        for (String paramName : paramNames) {
            paramNameValue.append(paramName).append(params.get(paramName));
        }

        String source = secret + paramNameValue.toString() + secret;

        return DigestUtils.md5Hex(source).toUpperCase();
    }
}
