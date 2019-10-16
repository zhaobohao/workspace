package com.gitee.sop.sdk.request;

import com.gitee.sop.sdk.response.OpenAuthTokenAppResponse;

/**
 * @author tanghc
 */
public class OpenAuthTokenAppRequest extends BaseRequest<OpenAuthTokenAppResponse> {
    @Override
    protected String method() {
        return "open.auth.token.app";
    }
}
