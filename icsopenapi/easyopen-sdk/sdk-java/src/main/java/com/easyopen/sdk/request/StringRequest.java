package com.easyopen.sdk.request;

import com.easyopen.sdk.response.StringResponse;

/**
 * 请求字符串
 * @author tanghc
 */
public class StringRequest extends BaseRequest<StringResponse> {

    public StringRequest(String name) {
        this.setName(name);
    }

    public StringRequest(String name, String version) {
        this.setName(name);
        this.setVersion(version);
    }

    @Override
    public String name() {
        return "";
    }
}
