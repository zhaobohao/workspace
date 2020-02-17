package com.xcbeyond.springboot.grpc.client.controller;

import com.xcbeyond.springboot.grpc.client.HelloGrpcClient;
import com.xcbeyond.springboot.grpc.client.SeeGrpcClient;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

/**
 * @Auther: xcbeyond
 * @Date: 2019/3/7 11:44
 */
@RestController
public class GrpcClientController {
    @Resource
    private HelloGrpcClient helloGrpcClient;
    @Resource
    private SeeGrpcClient seeGrpcClient;
    @RequestMapping("/")
    public String printMessage(@RequestParam(defaultValue = "jack") String name) {
        return helloGrpcClient.sendMessage(name);
    }

    @RequestMapping("/see")
    public String printSeeMessage(@RequestParam(defaultValue = "Marry") String name) {
        return seeGrpcClient.seeMessage(name);
    }
}
