package com.gitee.sop.servercommon.configuration;

import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.manager.ServiceRouteController;
import com.gitee.sop.servercommon.mapping.ApiMappingHandlerMapping;
import com.gitee.sop.servercommon.message.ServiceErrorFactory;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Primary;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.annotation.PostConstruct;

/**
 * 提供给springmvc工程
 * @author tanghc
 */
@Slf4j
public class SpringMvcServiceConfiguration {

    public SpringMvcServiceConfiguration() {
        ServiceConfig.getInstance().getI18nModules().add("i18n/isp/bizerror");
    }

    private ApiMappingHandlerMapping apiMappingHandlerMapping = new ApiMappingHandlerMapping();

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
