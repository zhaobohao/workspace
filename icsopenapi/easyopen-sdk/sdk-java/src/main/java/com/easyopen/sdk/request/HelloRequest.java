package com.easyopen.sdk.request;

import com.easyopen.sdk.response.HelloResponse;

public class HelloRequest extends BaseRequest<HelloResponse> {
    @Override
    public String name() {
        return "hello";
    }
}
