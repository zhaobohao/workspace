package com.gitee.sop.gateway.interceptor;

import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptor;
import com.gitee.sop.gatewaycommon.interceptor.RouteInterceptorContext;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import org.springframework.stereotype.Component;

/**
 * 演示拦截器
 *
 * @author tanghc
 */
@Component
public class MyRouteInterceptor implements RouteInterceptor {

    @Override
    public void preRoute(RouteInterceptorContext context) {
        ApiParam apiParam = context.getApiParam();
        System.out.println("请求接口：" + apiParam.fetchNameVersion());
    }

    @Override
    public void afterRoute(RouteInterceptorContext context) {
        System.out.println("请求成功，微服务返回结果：" + context.getServiceResult());
    }

    @Override
    public int getOrder() {
        return 0;
    }

}
