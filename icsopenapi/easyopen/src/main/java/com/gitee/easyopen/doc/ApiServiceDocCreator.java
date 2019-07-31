package com.gitee.easyopen.doc;

import com.gitee.easyopen.annotation.Api;
import com.gitee.easyopen.annotation.ApiService;
import org.springframework.context.ApplicationContext;

/**
 * @author tanghc
 */
public class ApiServiceDocCreator extends AbstractApiDocCreator<ApiService, Api> {

    public ApiServiceDocCreator(String defaultVersion, ApplicationContext applicationContext) {
        super(defaultVersion, applicationContext);
    }

    @Override
    protected Class<ApiService> getServiceAnnotationClass() {
        return ApiService.class;
    }

    @Override
    protected Class<Api> getMethodAnnotationClass() {
        return Api.class;
    }

    @Override
    protected com.gitee.easyopen.bean.Api getApi(Api api) {
        return new com.gitee.easyopen.bean.Api(api.name(), api.version());
    }
}
