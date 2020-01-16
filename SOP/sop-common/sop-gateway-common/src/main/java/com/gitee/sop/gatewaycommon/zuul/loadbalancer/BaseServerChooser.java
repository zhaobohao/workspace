package com.gitee.sop.gatewaycommon.zuul.loadbalancer;

import com.gitee.sop.gatewaycommon.bean.SpringContext;
import com.gitee.sop.gatewaycommon.manager.EnvGrayManager;
import com.google.common.base.Optional;
import com.netflix.loadbalancer.ILoadBalancer;
import com.netflix.loadbalancer.Server;
import com.netflix.loadbalancer.ZoneAvoidanceRule;
import com.netflix.zuul.context.RequestContext;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.ArrayUtils;
import org.springframework.core.env.Environment;
import org.springframework.util.StringUtils;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;

/**
 * 服务实例选择器
 *
 * @author tanghc
 */
@Slf4j
public abstract class BaseServerChooser extends ZoneAvoidanceRule {


    /**
     * 是否是预发布服务器
     * @param server 服务器
     * @return true：是
     */
    protected abstract boolean isPreServer(Server server);

    /**
     * 是否是灰度发布服务器
     * @param server 服务器
     * @return true：是
     */
    protected abstract boolean isGrayServer(Server server);

    /**
     * 能否访问预发布
     * @param server 预发布服务器
     * @param request request
     * @return true：能
     */
    protected abstract boolean canVisitPre(Server server, HttpServletRequest request);


    @Override
    public Server choose(Object key) {
        ILoadBalancer lb = getLoadBalancer();
        // 获取服务实例列表
        List<Server> servers = lb.getReachableServers();
        HttpServletRequest request = RequestContext.getCurrentContext().getRequest();
        // 存放预发服务器
        List<Server> preServers = new ArrayList<>(4);
        // 存放灰度发布服务器
        List<Server> grayServers = new ArrayList<>(4);
        // 存放非预发服务器
        List<Server> notPreServers = new ArrayList<>(4);

        for (Server server : servers) {
            // 是否开启了预发模式
            if (this.isPreServer(server)) {
                preServers.add(server);
            } else if (this.isGrayServer(server)) {
                grayServers.add(server);
            } else {
                notPreServers.add(server);
            }
        }
        notPreServers.addAll(grayServers);
        // 如果没有开启预发布服务和灰度发布，直接用默认的方式
        if (preServers.isEmpty() && grayServers.isEmpty()) {
            return super.choose(key);
        }
        // 如果是从预发布域名访问过来，则认为是预发布请求
        if (this.isRequestFromPreDomain(request)) {
            return doChoose(preServers, key);
        }
        // 如果是灰度请求
        if (this.isRequestGrayServer(RequestContext.getCurrentContext())) {
            return doChoose(grayServers, key);
        }

        // 到这里说明不能访问预发/灰度服务器，则需要路由到非预发服务器
        return doChoose(notPreServers, key);
    }

    /**
     * 通过判断hostname来确定是否是预发布请求
     *
     * @param t t
     * @return 返回true：可以进入到预发环境
     */
    protected boolean isRequestFromPreDomain(HttpServletRequest t) {
        String domain = SpringContext.getBean(Environment.class).getProperty("pre.domain");
        if (StringUtils.isEmpty(domain)) {
            return false;
        }
        String[] domains = domain.split("\\,");
        return ArrayUtils.contains(domains, getHost(t));
    }

    protected boolean isRequestGrayServer(RequestContext t) {
        return t.get(EnvGrayManager.ENV_GRAY) != null;
    }

    protected String getHost(HttpServletRequest t) {
        return t.getServerName();
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
