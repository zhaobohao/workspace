package com.gitee.apiconf;

import com.gitee.apiconf.netty.NettyServer;
import com.gitee.apiconf.service.GlobalConfigService;
import org.apache.commons.lang.math.NumberUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;

/**
 * @author tanghc
 */
@Component
public class OnPostConstruct {
    Logger logger = LoggerFactory.getLogger(getClass());

    @Autowired
    GlobalConfigService globalConfigService;

    @Value("${netty.server.port}")
    private String nettyPort;

    @PostConstruct
    public void init() {
        try {
            logger.info("call @PostConstruct...");
            globalConfigService.init();
            // 启动Netty服务端
            int port = NumberUtils.toInt(nettyPort);
            new NettyServer(port).startup();
        } catch (Exception e) {
            logger.error("初始化失败", e);
            System.exit(0);
        }
    }

}
