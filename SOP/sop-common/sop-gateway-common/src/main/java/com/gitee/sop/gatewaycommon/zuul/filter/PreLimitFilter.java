package com.gitee.sop.gatewaycommon.zuul.filter;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.bean.ConfigLimitDto;
import com.gitee.sop.gatewaycommon.exception.ApiException;
import com.gitee.sop.gatewaycommon.limit.LimitManager;
import com.gitee.sop.gatewaycommon.limit.LimitType;
import com.gitee.sop.gatewaycommon.manager.LimitConfigManager;
import com.gitee.sop.gatewaycommon.message.ErrorImpl;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.util.RequestUtil;
import com.gitee.sop.gatewaycommon.zuul.ZuulContext;
import com.netflix.zuul.context.RequestContext;
import com.netflix.zuul.exception.ZuulException;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * 限流拦截器
 *
 * @author tanghc
 */
public class PreLimitFilter extends BaseZuulFilter {

    @Autowired
    private LimitManager limitManager;

    @Autowired
    private LimitConfigManager limitConfigManager;

    @Override
    protected FilterType getFilterType() {
        return FilterType.PRE;
    }

    @Override
    protected int getFilterOrder() {
        return PRE_LIMIT_FILTER_ORDER;
    }

    @Override
    protected Object doRun(RequestContext requestContext) throws ZuulException {
        ApiConfig apiConfig = ApiConfig.getInstance();
        // 限流功能未开启，直接返回
        if (!apiConfig.isOpenLimit()) {
            return null;
        }
        ApiParam apiParam = ZuulContext.getApiParam();
        ConfigLimitDto configLimitDto = this.findConfigLimitDto(apiConfig, apiParam, requestContext.getRequest());
        if (configLimitDto == null) {
            return null;
        }
        // 单个限流功能未开启
        if (configLimitDto.getLimitStatus() == ConfigLimitDto.LIMIT_STATUS_CLOSE) {
            return null;
        }
        byte limitType = configLimitDto.getLimitType();
        // 如果是漏桶策略
        if (limitType == LimitType.LEAKY_BUCKET.getType()) {
            boolean acquire = limitManager.acquire(configLimitDto);
            if (!acquire) {
                throw new ApiException(new ErrorImpl(configLimitDto.getLimitCode(), configLimitDto.getLimitMsg()));
            }
        } else if (limitType == LimitType.TOKEN_BUCKET.getType()) {
            limitManager.acquireToken(configLimitDto);
        }
        return null;
    }

    protected ConfigLimitDto findConfigLimitDto(ApiConfig apiConfig, ApiParam apiParam, HttpServletRequest request) {
        String routeId = apiParam.fetchNameVersion();
        String appKey = apiParam.fetchAppKey();
        String ip = RequestUtil.getIP(request);

        // 最多7种情况
        String[] limitKeys = new String[]{
                // 根据路由ID限流
                routeId,
                // 根据appKey限流
                appKey,
                // 根据路由ID + appKey限流
                routeId + appKey,

                // 根据ip限流
                ip,
                // 根据ip+路由id限流
                ip + routeId,
                // 根据ip+appKey限流
                ip + appKey,
                // 根据ip+路由id+appKey限流
                ip + routeId + appKey,
        };

        List<ConfigLimitDto> limitConfigList = new ArrayList<>();
        for (String limitKey : limitKeys) {
            ConfigLimitDto configLimitDto = limitConfigManager.get(limitKey);
            if (configLimitDto == null) {
                continue;
            }
            if (configLimitDto.getLimitStatus().intValue() == ConfigLimitDto.LIMIT_STATUS_OPEN) {
                limitConfigList.add(configLimitDto);
            }
        }
        if (limitConfigList.isEmpty()) {
            return null;
        }
        limitConfigList.sort(Comparator.comparing(ConfigLimitDto::getOrderIndex));
        return limitConfigList.get(0);
    }
}
