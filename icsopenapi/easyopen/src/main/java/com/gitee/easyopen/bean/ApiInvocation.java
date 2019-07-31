package com.gitee.easyopen.bean;

import com.gitee.easyopen.ApiMeta;

/**
 * @author tanghc
 */
public class ApiInvocation implements Invocation {

    private ApiMeta apiMeta;
    private Object argu;

    public ApiInvocation(ApiMeta apiMeta, Object argu) {
        this.apiMeta = apiMeta;
        this.argu = argu;
    }

    @Override
    public ApiMeta getApiMeta() {
        return apiMeta;
    }

    @Override
    public Object getArgu() {
        return argu;
    }

    @Override
    public Object process() throws Exception {
        Object invokeResult;
        if (argu == null) {
            invokeResult = apiMeta.getMethod().invoke(apiMeta.getHandler());
        } else {
            invokeResult = apiMeta.getMethod().invoke(apiMeta.getHandler(), argu);
        }
        return invokeResult;
    }
}
