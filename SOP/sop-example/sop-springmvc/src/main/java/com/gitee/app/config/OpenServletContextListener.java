package com.gitee.app.config;

import com.alibaba.nacos.api.exception.NacosException;
import com.alibaba.nacos.api.naming.NamingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * 容器销毁注销nacos，配置见web.xml
 */
@Slf4j
public class OpenServletContextListener implements ServletContextListener {

    private static WebApplicationContext webApplicationContext;

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        String serviceId = System.getProperty(OpenServiceConfig.SPRING_APPLICATION_NAME);
        String ip = System.getProperty(OpenServiceConfig.SERVER_IP);
        String port = System.getProperty(OpenServiceConfig.SERVER_PORT);

        log.info("注销nacos，serviceId:{}, ip:{}, port:{}", serviceId, ip, port);

        NamingService namingService = webApplicationContext.getBean(NamingService.class);
        try {
            namingService.deregisterInstance(serviceId, ip, Integer.valueOf(port));
        } catch (NacosException e) {
            log.error("注销nacos服务失败，serviceId:{}, ip:{}, port:{}", serviceId, ip, port);
        }
    }

    @Override
    public void contextInitialized(ServletContextEvent sce) {
        webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(sce.getServletContext());
    }

}