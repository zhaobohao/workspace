package com.gitee.sop.gatewaycommon.zuul.loadbalancer;

import com.gitee.sop.gatewaycommon.loadbalancer.LoadBalanceServerChooser;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.loadbalancer.Server;

import javax.servlet.http.HttpServletRequest;

/**
 * @author tanghc
 */
public class ZuulLoadBalanceServerChooser extends LoadBalanceServerChooser<HttpServletRequest, Server> {

    @Override
    public String getHost(HttpServletRequest request) {
        return request.getServerName();
    }

    @Override
    public ApiParam getApiParam(HttpServletRequest request) {
        return ZuulContext.getApiParam();
    }
}
