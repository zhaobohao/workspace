package org.springclouddev.iot.driver.service.impl;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.iot.common.sdk.bean.AttributeInfo;
import org.springclouddev.iot.common.sdk.service.CustomDriverService;
import org.springclouddev.iot.driver.service.netty.NettyUdpServer;
import org.springclouddev.iot.manager.entity.Device;
import org.springclouddev.iot.manager.entityint;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Service
public class CustomDriverServiceImpl implements CustomDriverService {

    @Value("${driver.custom.udp.port}")
    private Integer port;

    @Resource
    private NettyUdpServer nettyUdpServer;
    @Resource
    private ThreadPoolExecutor threadPoolExecutor;

    @Override
    public void initial() {
        threadPoolExecutor.execute(() -> {
            log.debug("Water 188B Udp Driver Starting(::{}) incoming data listener", port);
            nettyUdpServer.start(port);
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
