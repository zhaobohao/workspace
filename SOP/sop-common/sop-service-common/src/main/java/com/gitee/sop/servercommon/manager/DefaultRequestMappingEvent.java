package com.gitee.sop.servercommon.manager;

import com.gitee.sop.servercommon.bean.ServiceApiInfo;
import lombok.Getter;
import org.springframework.core.env.Environment;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * @author tanghc
 */
@Getter
public class DefaultRequestMappingEvent extends ApiMetaBuilder implements RequestMappingEvent {

    private ApiMetaManager apiMetaManager;
    private Environment environment;

    public DefaultRequestMappingEvent(ApiMetaManager apiMetaManager, Environment environment) {
        this.apiMetaManager = apiMetaManager;
        this.environment = environment;
    }

    @Override
    public void onRegisterSuccess(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        String serviceId = environment.getProperty("spring.application.name");
        ServiceApiInfo serviceApiInfo = getServiceApiInfo(serviceId, requestMappingHandlerMapping);
        apiMetaManager.uploadApi(serviceApiInfo);
    }

}
