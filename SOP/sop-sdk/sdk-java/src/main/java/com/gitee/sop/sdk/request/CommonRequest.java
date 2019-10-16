package com.gitee.sop.sdk.request;

import com.gitee.sop.sdk.response.CommonResponse;

/**
 * @author tanghc
 */
public class CommonRequest extends BaseRequest<CommonResponse> {

    public CommonRequest(String method) {
        super(method, null);
    }

    public CommonRequest(String method, String version) {
        super(method, version);
    }

    @Override
    protected String method() {
        return "";
    }
}
