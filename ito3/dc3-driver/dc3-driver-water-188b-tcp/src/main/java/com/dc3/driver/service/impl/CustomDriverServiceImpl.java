package com.dc3.driver.service.impl;

import com.dc3.common.model.Device;
import com.dc3.common.model.Point;
import com.dc3.common.sdk.bean.AttributeInfo;
import com.dc3.common.sdk.service.CustomDriverService;
import com.dc3.driver.service.netty.NettyTcpServer;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Service
public class CustomDriverServiceImpl implements CustomDriverService {

    @Value("${driver.custom.tcp.port}")
    private Integer port;

    @Resource
    private NettyTcpServer nettyTcpServer;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void initial() {
        threadPoolExecutor.execute(() -> {
            log.debug("Water 188B Tcp Driver Starting(::{}) incoming data listener", port);
            nettyTcpServer.start(port);
        });
    }

    @Override
    public String read(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, Point point) throws Exception {
        return "nil";
    }

    @Override
    public Boolean write(Map<String, AttributeInfo> driverInfo, Map<String, AttributeInfo> pointInfo, Device device, AttributeInfo value) throws Exception {
        return false;
    }

    @Override
    public void schedule() {
        log.info("schedule not implemented");
    }

}
