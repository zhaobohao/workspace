package com.gitee.app.config;

import com.alibaba.nacos.api.annotation.NacosInjected;
import com.alibaba.nacos.api.annotation.NacosProperties;
import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import com.alibaba.nacos.api.naming.pojo.Instance;
import com.alibaba.nacos.client.naming.utils.NetUtils;
import com.alibaba.nacos.spring.context.annotation.discovery.EnableNacosDiscovery;
import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.configuration.SpringMvcServiceConfiguration;
import lombok.extern.slf4j.Slf4j;

/**
 * 使用支付宝开放平台功能
 *
 * @author tanghc
 */
@Slf4j
@EnableNacosDiscovery(globalProperties = @NacosProperties(serverAddr = "127.0.0.1:8848"))
public class OpenServiceConfig extends SpringMvcServiceConfiguration {


    public static final String SPRING_APPLICATION_NAME = "spring.application.name";
    public static final String SERVER_CONTEXT_PATH = "server.servlet.context-path";
    public static final String SERVER_IP = "server.ip";
    public static final String SERVER_PORT = "server.port";

    static {
        ServiceConfig.getInstance().setDefaultVersion("1.0");
    }

    /** 对应tomcat中的contextPath */
    private String contextPath = "/sop-springmvc";
    private String serviceId = "sop-springmvc";
    private int port = 2223;

    @NacosInjected
    private NamingService namingService;

    @Override
    protected void doAfter() {
        super.doAfter();
        try {
            String ip = NetUtils.localIP();
            System.setProperty(SPRING_APPLICATION_NAME, serviceId);
            System.setProperty(SERVER_IP, ip);
            System.setProperty(SERVER_PORT, String.valueOf(port));
            System.setProperty(SERVER_CONTEXT_PATH, contextPath);

            Instance instance = this.getInstance(serviceId, ip, port, contextPath);
            namingService.registerInstance(serviceId, instance);
            log.info("注册到nacos, serviceId: {}, ip: {}, port: {}, contextPath: {}", serviceId, ip, port, contextPath);
        } catch (NacosException e) {
            log.error("注册nacos失败", e);
            throw new RuntimeException("注册nacos失败", e);
        }
    }

    private Instance getInstance(String serviceId, String ip, int port, String contextPath) {
        Instance instance = new Instance();
        instance.setServiceName(serviceId);
        instance.setIp(ip);
        instance.setPort(port);
        instance.getMetadata().put(SERVER_CONTEXT_PATH, contextPath);
        return instance;
    }

}
