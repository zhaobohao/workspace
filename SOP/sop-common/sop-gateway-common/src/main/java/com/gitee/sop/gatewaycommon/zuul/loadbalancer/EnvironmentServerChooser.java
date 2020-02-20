package com.gitee.sop.gatewaycommon.zuul.loadbalancer;

import com.gitee.sop.gatewaycommon.loadbalancer.ServerChooserContext;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.google.common.base.Optional;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 预发布、灰度环境选择，参考自：https://segmentfault.com/a/1190000017412946
 *
 * @author tanghc
 */
@Slf4j
public class EnvironmentServerChooser extends ZoneAvoidanceRule implements ServerChooserContext<HttpServletRequest> {

    private ZuulLoadBalanceServerChooser loadBalanceServerChooser = new ZuulLoadBalanceServerChooser();

    @Override
    public String getHost(HttpServletRequest request) {
        return request.getServerName();
    }

    @Override
    public ApiParam getApiParam(HttpServletRequest request) {
        return ZuulContext.getApiParam();
    }

    @Override
    public Server choose(Object key) {
        return loadBalanceServerChooser.choose(
                String.valueOf(key)
                , RequestContext.getCurrentContext().getRequest()
                , getLoadBalancer()
                , () -> super.choose(key)
                , (servers) -> this.doChoose(servers, key)
        );
    }

    protected Server doChoose(List<Server> servers, Object key) {
        Optional<Server> server = getPredicate().chooseRoundRobinAfterFiltering(servers, key);
        if (server.isPresent()) {
            return server.get();
        } else {
            return null;
        }
    }

}
