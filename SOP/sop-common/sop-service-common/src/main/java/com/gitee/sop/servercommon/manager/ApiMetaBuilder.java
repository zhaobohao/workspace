package com.gitee.sop.servercommon.manager;

import com.gitee.sop.servercommon.bean.ServiceApiInfo;
import com.gitee.sop.servercommon.bean.ServiceConfig;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.mapping.ApiMappingInfo;
import com.gitee.sop.servercommon.mapping.ApiMappingRequestCondition;
import com.gitee.sop.servercommon.mapping.RouteUtil;
import org.apache.commons.lang3.BooleanUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.condition.RequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Set;

/**
 * @author tanghc
 */
public class ApiMetaBuilder {

    /** 接口名规则：允许字母、数字、点、下划线 */
    private static final String REGEX_API_NAME = "^[a-zA-Z0-9\\.\\_\\-]+$";

    private static final List<String> IGNORE_PATTERN_KEYWORDS = Arrays.asList("swagger","sop/routes");

    public ServiceApiInfo getServiceApiInfo(String serviceId, RequestMappingHandlerMapping requestMappingHandlerMapping) {
        if (serviceId == null) {
            throw new IllegalArgumentException("请在application.properties中指定spring.application.name属性");
        }
        List<ServiceApiInfo.ApiMeta> apis = this.buildApiMetaList(requestMappingHandlerMapping);
        // 排序
        apis.sort(Comparator.comparing(ServiceApiInfo.ApiMeta::fetchNameVersion));

        ServiceApiInfo serviceApiInfo = new ServiceApiInfo();
        serviceApiInfo.setServiceId(serviceId);
        serviceApiInfo.setApis(apis);

        return serviceApiInfo;
    }

    protected List<ServiceApiInfo.ApiMeta> buildApiMetaList(RequestMappingHandlerMapping requestMappingHandlerMapping) {
        Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
        Set<RequestMappingInfo> requestMappingInfos = handlerMethods.keySet();
        List<String> store = new ArrayList<>();
        List<ServiceApiInfo.ApiMeta> apis = new ArrayList<>(requestMappingInfos.size());

        for (Map.Entry<RequestMappingInfo, HandlerMethod> handlerMethodEntry : handlerMethods.entrySet()) {
            ServiceApiInfo.ApiMeta apiMeta = this.buildApiMeta(handlerMethodEntry);
            if (apiMeta == null) {
                continue;
            }
            String key = apiMeta.fetchNameVersion();
            if (store.contains(key)) {
                throw new IllegalArgumentException("重复申明接口，请检查path和version，path:" + apiMeta.getPath() + ", version:" + apiMeta.getVersion());
            } else {
                store.add(key);
            }
            apis.add(apiMeta);
        }
        return apis;
    }

    protected ServiceApiInfo.ApiMeta buildApiMeta(Map.Entry<RequestMappingInfo, HandlerMethod> handlerMethodEntry) {
        RequestMappingInfo requestMappingInfo = handlerMethodEntry.getKey();
        Set<String> patterns = requestMappingInfo.getPatternsCondition().getPatterns();
        RequestCondition<?> customCondition = requestMappingInfo.getCustomCondition();
        if (customCondition instanceof ApiMappingRequestCondition) {
            ApiMappingRequestCondition condition = (ApiMappingRequestCondition) customCondition;
            ApiMappingInfo apiMappingInfo = condition.getApiMappingInfo();
            String name = apiMappingInfo.getName();
            String version = apiMappingInfo.getVersion();
            // 方法完整的path，如: /goods/listGoods,/users/user/get
            String path = patterns.iterator().next();
            // 不是ApiMapping注解的接口，name属性是null
            if (name == null || ServiceConfig.getInstance().isWebappMode()) {
                name = buildName(path);
            }
            this.checkApiName(name);
            ServiceApiInfo.ApiMeta apiMeta = new ServiceApiInfo.ApiMeta(name, path, version);
            apiMeta.setIgnoreValidate(BooleanUtils.toInteger(apiMappingInfo.isIgnoreValidate()));
            apiMeta.setMergeResult(BooleanUtils.toInteger(apiMappingInfo.isMergeResult()));
            apiMeta.setPermission(BooleanUtils.toInteger(apiMappingInfo.isPermission()));
            return apiMeta;
        } else {
            if (!ServiceContext.getCurrentContext().getBoolean(ServiceContext.RESTFUL_KEY, false)) {
                return null;
            }
            // 如果是restful服务
            String path = patterns.iterator().next();
            if (path.contains("$") || isIgnorePattern(path)) {
                return null;
            }
            String name = path;
            String prefix = EnvironmentKeys.SOP_RESTFUL_PREFIX.getValue();
            if (StringUtils.isEmpty(prefix)) {
                prefix = EnvironmentKeys.SPRING_APPLICATION_NAME.getValue();
            }
            String oldModel = EnvironmentKeys.SOP_RESTFUL_OLD_MODEL.getValue();
            if (!"true".equals(oldModel) && StringUtils.hasText(prefix)) {
                name = "/" + prefix + "/" + StringUtils.trimLeadingCharacter(path, '/');
            }
            ServiceApiInfo.ApiMeta apiMeta = new ServiceApiInfo.ApiMeta(name, path, "");
            apiMeta.setIgnoreValidate(BooleanUtils.toInteger(true));
            apiMeta.setMergeResult(BooleanUtils.toInteger(false));
            apiMeta.setPermission(BooleanUtils.toInteger(false));
            apiMeta.setOriginalMapping(true);
            return apiMeta;
        }
    }

    private boolean isIgnorePattern(String pattern) {
        for (String keyword : IGNORE_PATTERN_KEYWORDS) {
            if (pattern.contains(keyword)) {
                return true;
            }
        }
        return false;
    }

    protected void checkApiName(String name) {
        if (!name.matches(REGEX_API_NAME)) {
            throw new IllegalArgumentException("接口名称只允许【字母、数字、点(.)、下划线(_)、减号(-)】，错误接口：" + name);
        }
    }


    protected String buildName(String path) {
        return RouteUtil.buildApiName(path);
    }
}
