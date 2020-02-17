package com.xcbeyond.springboot.grpc.client;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import com.xcbeyond.springboot.grpc.lib.HelloReply;
import com.xcbeyond.springboot.grpc.lib.HelloRequest;
import com.xcbeyond.springboot.grpc.lib.SimpleGrpc.SimpleBlockingStub;
import io.grpc.StatusRuntimeException;
import net.devh.boot.grpc.client.inject.GrpcClient;
import org.springclouddev.core.cloud.sentinel.utils.ExceptionUtil;
import org.springframework.stereotype.Service;

/**
 * @Auther: xcbeyond
 * @Date: 2019/3/7 09:10
 */
@Service
public class HelloGrpcClient {

    @GrpcClient("spring-boot-grpc-server")
    private SimpleBlockingStub simpleBlockingStub;

    @SentinelResource(value = "test", blockHandler = "exceptionHandler", blockHandlerClass = {ExceptionUtil.class}, fallback = "helloFallback")
    public String sendMessage(String name) {
        try {
            HelloReply response = simpleBlockingStub.sayHello(HelloRequest.newBuilder().setName(name).build());
            return response.getMessage();
        } catch (final StatusRuntimeException e) {
            return "FAILED with " + e.getStatus().getCode();
        }
    }

    // Fallback 函数，函数签名与原函数一致或加一个 Throwable 类型的参数.
    public String helloFallback(String s) {
        return String.format("Halooooo %d", s);
    }

    // Block 异常处理函数，参数最后多一个 BlockException，其余与原函数一致.
    public String exceptionHandler(String s, BlockException ex) {
        // Do some log here.
        ex.printStackTrace();
        return "Oops, error occurred at " + s;
    }

}
