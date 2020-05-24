package com.gitee.sop.servercommon.mapping;

import com.gitee.sop.servercommon.annotation.ApiAbility;
import com.gitee.sop.servercommon.annotation.ApiMapping;
import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.util.OpenUtil;
import org.springframework.core.PriorityOrdered;
import org.springframework.util.StringValueResolver;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;

/**
 * @author tanghc
 */
public class ApiMappingHandlerMapping extends RequestMappingHandlerMapping implements PriorityOrdered {

    private static StringValueResolver stringValueResolverMVC = new ApiMappingStringValueResolverMVC();

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        String sopMvc = System.getProperty(ServiceContext.SOP_MVC);
        boolean isMvc = sopMvc != null;
        ApiMapping apiMapping = method.getAnnotation(ApiMapping.class);
        ApiAbility apiAbility = OpenUtil.getAnnotationFromMethodOrClass(method, ApiAbility.class);
        StringValueResolver valueResolver = null;
        if (apiMapping != null) {
            String version = apiMapping.version();
            if ("".equals(version)) {
                version = ServiceConfig.getInstance().getDefaultVersion();
            }
            valueResolver = new ApiMappingStringValueResolverVersion(version);
        }
        if (isMvc || apiAbility != null) {
            valueResolver = stringValueResolverMVC;
        }
        this.setEmbeddedValueResolver(valueResolver);
        return super.getMappingForMethod(method, handlerType);
    }

    @Override
    protected RequestCondition<?> getCustomMethodCondition(Method method) {
        method.setAccessible(true);
        String name = null;
        String version;
        boolean ignoreValidate;
        boolean mergeResult;
        boolean permission;
        boolean needToken;
        boolean compatibleMode = false;
        ApiMapping apiMapping = method.getAnnotation(ApiMapping.class);
        if (apiMapping != null) {
            name = apiMapping.value()[0];
            version = apiMapping.version();
            ignoreValidate = apiMapping.ignoreValidate();
            mergeResult = apiMapping.mergeResult();
            permission = apiMapping.permission();
            needToken = apiMapping.needToken();
        } else {
            ApiAbility apiAbility = OpenUtil.getAnnotationFromMethodOrClass(method, ApiAbility.class);
            if (apiAbility != null) {
                version = apiAbility.version();
                ignoreValidate = apiAbility.ignoreValidate();
                mergeResult = apiAbility.mergeResult();
                permission = apiAbility.permission();
                needToken = apiAbility.needToken();
                compatibleMode = true;
            } else {
                return super.getCustomMethodCondition(method);
            }
        }
        if ("".equals(version)) {
            version = ServiceConfig.getInstance().getDefaultVersion();
        }
        ApiMappingInfo apiMappingInfo = new ApiMappingInfo(name, version);
        // 如果是默认配置，则采用全局配置
        if (!ignoreValidate) {
            ignoreValidate = ServiceConfig.getInstance().isIgnoreValidate();
        }
        // 如果是默认配置，则采用全局配置
        if (mergeResult) {
            mergeResult = ServiceConfig.getInstance().isMergeResult();
        }
        // 如果是默认配置，则采用全局配置
        if (!permission) {
            permission =  ServiceConfig.getInstance().isPermission();
        }
        apiMappingInfo.setIgnoreValidate(ignoreValidate);
        apiMappingInfo.setMergeResult(mergeResult);
        apiMappingInfo.setPermission(permission);
        apiMappingInfo.setNeedToken(needToken);
        apiMappingInfo.setCompatibleMode(compatibleMode);
        logger.info("注册接口，name:" + method + "， version:" + version);
        return new ApiMappingRequestCondition(apiMappingInfo);
    }

}