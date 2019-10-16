package com.gitee.sop.websiteserver.config;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.config.FastJsonConfig;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.gitee.sop.gatewaycommon.route.EurekaRegistryListener;
import com.gitee.sop.gatewaycommon.route.NacosRegistryListener;
import com.gitee.sop.gatewaycommon.route.RegistryListener;
import com.gitee.sop.gatewaycommon.route.ServiceListener;
import com.gitee.sop.websiteserver.listener.ServiceDocListener;
import com.gitee.sop.websiteserver.manager.DocManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.http.HttpMessageConverters;
import org.springframework.cloud.client.discovery.event.HeartbeatEvent;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.event.EventListener;

/**
 * @author tanghc
 */
@Configuration
public class WebsiteConfig implements ApplicationRunner {

    @Autowired
    DocManager docManager;

    @Autowired
    private RegistryListener registryListener;

    /**
     * 使用fastjson代替jackson
     * @return
     */
    @Bean
    public HttpMessageConverters fastJsonConfigure(){
        FastJsonHttpMessageConverter converter = new FastJsonHttpMessageConverter();
        FastJsonConfig fastJsonConfig = new FastJsonConfig();
        fastJsonConfig.setSerializerFeatures(SerializerFeature.WriteNullStringAsEmpty, SerializerFeature.WriteMapNullValue);
        // 日期格式化
        fastJsonConfig.setDateFormat("yyyy-MM-dd HH:mm:ss");
        converter.setFastJsonConfig(fastJsonConfig);
        return new HttpMessageConverters(converter);
    }

    /**
     * nacos事件监听
     *
     * @param heartbeatEvent
     */
    @EventListener(classes = HeartbeatEvent.class)
    public void listenNacosEvent(ApplicationEvent heartbeatEvent) {
        registryListener.onEvent(heartbeatEvent);
    }

    /**
     * 微服务路由加载
     */
    @Bean
    @ConditionalOnProperty("spring.cloud.nacos.discovery.server-addr")
    RegistryListener registryListenerNacos() {
        return new NacosRegistryListener();
    }

    @Bean
    @ConditionalOnProperty("eureka.client.serviceUrl.defaultZone")
    RegistryListener registryListenerEureka() {
        return new EurekaRegistryListener();
    }

    @Bean
    @ConditionalOnMissingBean
    ServiceListener serviceListener() {
        return new ServiceDocListener();
    }

    /**
     * SpringBoot启动完毕执行
     */
    @Override
    public void run(ApplicationArguments args) throws Exception {
//        docManager.load(null);
    }
}
