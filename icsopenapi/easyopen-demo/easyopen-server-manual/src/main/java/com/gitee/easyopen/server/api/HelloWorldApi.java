package com.gitee.easyopen.server.api;

import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;

@ApiService
public class HelloWorldApi {
    @Api(name = "hello")
    public String helloworld() {
        return "hello world";
    }
}
