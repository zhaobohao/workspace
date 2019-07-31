package com.easyopen.sdk.request;

import com.easyopen.sdk.response.CommonResponse;

/**
 * @author tanghc
 */
public class CommonRequest extends BaseRequest<CommonResponse> {

    public CommonRequest(String name) {
        this.setName(name);
    }

    public CommonRequest(String name, String version) {
        this.setName(name);
        this.setVersion(version);
    }

    @Override
    public String name() {
        return "";
    }
}
