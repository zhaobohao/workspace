package com.dc3.common.sdk.api;

import com.dc3.common.bean.RequestApi;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.BeanFactoryUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.WebApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerMapping;
import org.springframework.web.servlet.mvc.condition.PatternsRequestCondition;
import org.springframework.web.servlet.mvc.condition.RequestMethodsRequestCondition;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.ServletContext;
import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Slf4j
@RestController
public class IndexApi {

    @GetMapping("/")
    public List<RequestApi> index(HttpServletRequest request) {
        List<RequestApi> requestApiList = new ArrayList<>();
        ServletContext servletContext = request.getSession().getServletContext();
        if (servletContext == null) {
            return requestApiList;
        }
        WebApplicationContext webApplicationContext = WebApplicationContextUtils.getWebApplicationContext(servletContext);
        if (webApplicationContext == null) {
            return requestApiList;
        }

        Map<String, HandlerMapping> handlerMappingMap = BeanFactoryUtils.beansOfTypeIncludingAncestors(webApplicationContext, HandlerMapping.class, true, false);

        for (HandlerMapping handlerMapping : handlerMappingMap.values()) {
            if (handlerMapping instanceof RequestMappingHandlerMapping) {
                RequestMappingHandlerMapping requestMappingHandlerMapping = (RequestMappingHandlerMapping) handlerMapping;
                Map<RequestMappingInfo, HandlerMethod> handlerMethods = requestMappingHandlerMapping.getHandlerMethods();
                for (Map.Entry<RequestMappingInfo, HandlerMethod> requestMappingInfoHandlerMethodEntry : handlerMethods.entrySet()) {
                    RequestMappingInfo requestMappingInfo = requestMappingInfoHandlerMethodEntry.getKey();
                    PatternsRequestCondition patternsCondition = requestMappingInfo.getPatternsCondition();
                    String requestUrl = patternsCondition.getPatterns().stream().map(String::valueOf).collect(Collectors.joining(","));
                    if ("/".equals(requestUrl)) {
                        continue;
                    }

                    RequestMethodsRequestCondition methodCondition = requestMappingInfo.getMethodsCondition();
                    String requestType = methodCondition.getMethods().stream().map(String::valueOf).collect(Collectors.joining(","));

                    HandlerMethod mappingInfoValue = requestMappingInfoHandlerMethodEntry.getValue();
                    String controllerName = mappingInfoValue.getBeanType().toString();
                    String requestMethodName = mappingInfoValue.getMethod().getName();
                    Class<?>[] methodParamTypes = mappingInfoValue.getMethod().getParameterTypes();

                    requestApiList.add(new RequestApi(requestUrl, requestType, controllerName, requestMethodName, methodParamTypes));
                }
            }
        }
        return requestApiList;
    }

}