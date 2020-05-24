package com.gitee.sop.servercommon.configuration;

import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.interceptor.ServiceContextInterceptor;
import com.gitee.sop.servercommon.manager.EnvironmentContext;
import com.gitee.sop.servercommon.manager.ServiceRouteController;
import com.gitee.sop.servercommon.mapping.ApiMappingHandlerMapping;
import com.gitee.sop.servercommon.message.ServiceErrorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author tanghc
 */
@Slf4j
public class BaseServiceConfiguration implements WebMvcConfigurer, WebMvcRegistrations {

    public BaseServiceConfiguration() {
        ServiceConfig.getInstance().getI18nModules().add("i18n/isp/bizerror");
    }

    private ApiMappingHandlerMapping apiMappingHandlerMapping = new ApiMappingHandlerMapping();

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
    public  void addInterceptors(InterceptorRegistry registry) {
        // 添加拦截器
        registry.addInterceptor(new ServiceContextInterceptor());
    }

    @Override
    public  void extendMessageConverters(List<HttpMessageConverter<?>> converters) {
        // 解决controller返回字符串中文乱码问题
        for (HttpMessageConverter<?> converter : converters) {
            if (converter instanceof StringHttpMessageConverter) {
                ((StringHttpMessageConverter)converter).setDefaultCharset(StandardCharsets.UTF_8);
            }
        }
    }

    /**
     * 自定义Mapping，详见@ApiMapping
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
