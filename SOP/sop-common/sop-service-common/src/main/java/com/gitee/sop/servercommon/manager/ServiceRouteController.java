package com.gitee.sop.servercommon.manager;

import com.gitee.sop.servercommon.bean.ServiceApiInfo;
import com.gitee.sop.servercommon.bean.ServiceContext;
import com.gitee.sop.servercommon.route.ServiceRouteInfo;
import com.gitee.sop.servercommon.util.OpenUtil;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author tanghc
 */
@Slf4j
@Getter
@RestController
public class ServiceRouteController {

    private static final String SECRET = "a3d9sf!1@odl90zd>fkASwq9i3jxnd";
    private static final String HEADER_RESTFUL = "restful";

    @Autowired
    private RequestMappingHandlerMapping requestMappingHandlerMapping;

    @Autowired
    private Environment environment;


    @RequestMapping("/sop/routes")
    public ServiceRouteInfo listRoutes(HttpServletRequest request, HttpServletResponse response) {
        if (!OpenUtil.validateSimpleSign(request, SECRET)) {
            log.error("签名验证失败, params:{}", request.getQueryString());
            return null;
        }
        return getServiceRouteInfo(request, response);
    }

    protected ApiMetaBuilder getApiMetaBuilder() {
        return new ApiMetaBuilder();
    }

    protected ServiceRouteInfo getServiceRouteInfo(HttpServletRequest request, HttpServletResponse response) {
        String serviceId = environment.getProperty("spring.application.name");
        if (serviceId == null) {
            throw new IllegalArgumentException("未指定spring.application.name参数");
        }
        ApiMetaBuilder apiMetaBuilder = getApiMetaBuilder();
        String restful = request.getHeader(HEADER_RESTFUL);
        boolean enableRestful = "true".equals(restful);
        ServiceContext.getCurrentContext().set(ServiceContext.RESTFUL_KEY, enableRestful);
        ServiceApiInfo serviceApiInfo = apiMetaBuilder.getServiceApiInfo(serviceId, requestMappingHandlerMapping);
        ServiceRouteInfoBuilder serviceRouteInfoBuilder = new ServiceRouteInfoBuilder(environment);
        return serviceRouteInfoBuilder.build(serviceApiInfo);
    }

}
