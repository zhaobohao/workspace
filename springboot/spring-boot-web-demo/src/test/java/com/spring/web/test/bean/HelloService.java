package com.spring.web.test.bean;

public interface HelloService {

    String getVal();

    //模拟远程调用，或者其他服务调用
    String getRemoteVal();
}
