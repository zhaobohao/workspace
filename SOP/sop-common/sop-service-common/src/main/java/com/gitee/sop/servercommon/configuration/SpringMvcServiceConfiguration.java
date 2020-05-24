package com.gitee.sop.servercommon.configuration;

import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.manager.EnvironmentContext;
import com.gitee.sop.servercommon.manager.ServiceRouteController;
import com.gitee.sop.servercommon.mapping.ApiMappingHandlerMapping;
import com.gitee.sop.servercommon.message.ServiceErrorFactory;
import com.gitee.sop.servercommon.param.SopHandlerMethodArgumentResolver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;

/**
 * 提供给springmvc工程
 * @author tanghc
 */
@Slf4j
public class SpringMvcServiceConfiguration {

    static {
        System.setProperty(ServiceContext.SOP_MVC, "true");
    }

    public SpringMvcServiceConfiguration() {
        ServiceConfig.getInstance().getI18nModules().add("i18n/isp/bizerror");
        // 默认版本号为1.0
        ServiceConfig.getInstance().setDefaultVersion("1.0");
    }

    private ApiMappingHandlerMapping apiMappingHandlerMapping = new ApiMappingHandlerMapping();

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;

    @Autowired
    private Environment environment;

    /**
     * 自定义Mapping，详见@ApiMapping
     *
     * @return 返回RequestMappingHandlerMapping
     */
    @Bean
    @Primary
    public RequestMappingHandlerMapping requestMappingHandlerMapping() {
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
        SopHandlerMethodArgumentResolver sopHandlerMethodArgumentResolver = ServiceConfig.getInstance().getMethodArgumentResolver();
        sopHandlerMethodArgumentResolver.setRequestMappingHandlerAdapter(requestMappingHandlerAdapter);
    }

    protected void initMessage() {
        ServiceErrorFactory.initMessageSource(ServiceConfig.getInstance().getI18nModules());
    }

}
