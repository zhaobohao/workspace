package com.gitee.sop.gatewaycommon.route;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.ApiParamAware;
import com.gitee.sop.gatewaycommon.bean.TargetRoute;
import com.gitee.sop.gatewaycommon.loadbalancer.builder.GrayUserBuilder;
import com.gitee.sop.gatewaycommon.manager.EnvGrayManager;
import com.gitee.sop.gatewaycommon.manager.RouteRepositoryContext;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

/**
 * @author tanghc
 */
public abstract class BaseForwardChooser<T> implements ForwardChooser<T>, ApiParamAware<T> {

    @Autowired
    private EnvGrayManager envGrayManager;

    @Override
    public ForwardInfo getForwardInfo(T t) {
        ApiParam apiParam = getApiParam(t);
        String nameVersion = apiParam.fetchNameVersion();
        TargetRoute targetRoute = RouteRepositoryContext.getRouteRepository().get(nameVersion);
        String serviceId = targetRoute.getServiceRouteInfo().fetchServiceIdLowerCase();
        // 如果服务在灰度阶段，返回一个灰度版本号
        String grayVersion = envGrayManager.getVersion(serviceId, nameVersion);
        // 如果是灰度环境
        if (grayVersion != null && isGrayUser(serviceId, apiParam)) {
            String newNameVersion = apiParam.fetchName() + grayVersion;
            TargetRoute targetRouteDest = RouteRepositoryContext.getRouteRepository().get(newNameVersion);
            if (targetRouteDest != null) {
                apiParam.setGrayRequest(true);
                targetRoute = targetRouteDest;
            }
        }
        return new ForwardInfo(targetRoute);
    }

    protected boolean isGrayUser(String serviceId, ApiParam apiParam) {
        List<GrayUserBuilder> grayUserBuilders = ApiConfig.getInstance().getGrayUserBuilders();
        for (GrayUserBuilder grayUserBuilder : grayUserBuilders) {
            String userKey = grayUserBuilder.buildGrayUserKey(apiParam);
            if (envGrayManager.containsKey(serviceId, userKey)) {
                return true;
            }
        }
        return false;
    }

}
