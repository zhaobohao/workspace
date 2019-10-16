package com.gitee.sop.gatewaycommon.loadbalancer;

import com.gitee.sop.gatewaycommon.bean.SpringContext;
import com.gitee.sop.gatewaycommon.zuul.loadbalancer.BaseServerChooser;
import com.netflix.loadbalancer.Server;
import org.springframework.cloud.alibaba.nacos.ribbon.NacosServer;
import org.springframework.core.env.Environment;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * 预发布、灰度环境选择，参考自：https://segmentfault.com/a/1190000017412946
 *
 * @author tanghc
 */
public class EnvironmentServerChooser extends BaseServerChooser {

    private static final String MEDATA_KEY_ENV = "env";
    private static final String ENV_PRE_VALUE = "pre";
    private static final String ENV_GRAY_VALUE = "gray";

    /**
     * 预发布机器域名
     */
    private static final String PRE_DOMAIN = "localhost";

    @Override
    protected boolean isPreServer(Server server) {
        String env = getEnvValue(server);
        return ENV_PRE_VALUE.equals(env);
    }

    @Override
    protected boolean isGrayServer(Server server) {
        String env = getEnvValue(server);
        return ENV_GRAY_VALUE.equals(env);
    }

    private String getEnvValue(Server server) {
        // eureka存储的metadata
        Map<String, String> metadata = getMetada(server);
        return metadata.get(MEDATA_KEY_ENV);
    }

    protected Map<String, String> getMetada(Server server) {
        return ((NacosServer) server).getMetadata();
    }


    /**
     * 通过判断hostname来确定是否是预发布请求，可修改此方法实现自己想要的
     *
     * @param request request
     * @return 返回true：可以进入到预发环境
     */
    @Override
    protected boolean canVisitPre(Server server, HttpServletRequest request) {
        String serverName = request.getServerName();
        String domain = SpringContext.getBean(Environment.class).getProperty("pre.domain", PRE_DOMAIN);
        return domain.equals(serverName);
    }

}
