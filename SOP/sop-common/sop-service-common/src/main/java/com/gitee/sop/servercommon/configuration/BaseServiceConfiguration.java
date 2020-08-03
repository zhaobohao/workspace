package com.gitee.sop.servercommon.configuration;

import com.alibaba.cloud.nacos.NacosDiscoveryProperties;
import com.alibaba.cloud.nacos.discovery.NacosWatch;
import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.interceptor.ServiceContextInterceptor;
import com.gitee.sop.servercommon.manager.EnvironmentContext;
import com.gitee.sop.servercommon.manager.ServiceRouteController;
import com.gitee.sop.servercommon.mapping.ApiMappingHandlerMapping;
import com.gitee.sop.servercommon.message.ServiceErrorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;

/**
 * @author tanghc
 */
@Slf4j
public class BaseServiceConfiguration implements WebMvcConfigurer, WebMvcRegistrations {

    public BaseServiceConfiguration() {
        ServiceConfig.getInstance().getI18nModules().add("i18n/isp/bizerror");
    }

    private final ApiMappingHandlerMapping apiMappingHandlerMapping = new ApiMappingHandlerMapping();
    @Autowired
    private Environment environment;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // 支持swagger-bootstrap-ui首页
        registry.addResourceHandler("doc.html").addResourceLocations("classpath:/META-INF/resources/");
        // 支持默认swagger
        registry.addResourceHandler("swagger-ui.html").addResourceLocations("classpath:/META-INF/resources/");
        registry.addResourceHandler("/webjars/**").addResourceLocations("classpath:/META-INF/resources/webjars/");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(new ServiceContextInterceptor());
    }

    @Override
    public void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决controller返回字符串中文乱码问题
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter) converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }

    @Bean
    @ConditionalOnMissingBean
    @ConditionalOnProperty("spring.cloud.nacos.discovery.server-addr")
    public NacosWatch nacosWatch(NacosDiscoveryProperties nacosDiscoveryProperties, ObjectProvider<TaskScheduler> taskScheduler, Environment environment) {
        Map<String, String> metadata = nacosDiscoveryProperties.getMetadata();
        String contextPath = environment.getProperty("server.servlet.context-path");
        // 将context-path信息加入到metadata中
        if (contextPath != null) {
            metadata.put("context-path", contextPath);
        }
        // 在元数据中新增启动时间，不能修改这个值，不然网关拉取接口会有问题
        nacosDiscoveryProperties.getMetadata().put("time.startup", String.valueOf(System.currentTimeMillis()));
        metadata.put("time.startup", String.valueOf(System.currentTimeMillis()));
        return new NacosWatch(nacosDiscoveryProperties, taskScheduler);
    }

    /**
     * 自定义Mapping，详见@ApiMapping
     *
     * @return 返回RequestMappingHandlerMapping
     */
    @Override
    public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
        return apiMappingHandlerMapping;
    }

    @Bean
    @ConditionalOnMissingBean
    GlobalExceptionHandler globalExceptionHandler() {
        return new GlobalExceptionHandler();
    }

    @Bean
    @ConditionalOnMissingBean
    ServiceRouteController serviceRouteInfoHandler() {
        return new ServiceRouteController();
    }

    @PostConstruct
    public final void after() {
        log.info("-----spring容器加载完毕-----");
        EnvironmentContext.setEnvironment(environment);
        initMessage();
        doAfter();
    }


    /**
     * spring容器加载完毕后执行
     */
    protected void doAfter() {

    }


    protected void initMessage() {
        ServiceErrorFactory.initMessageSource(ServiceConfig.getInstance().getI18nModules());
    }

}
