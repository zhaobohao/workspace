package com.gitee.easyopen.support;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.ApiParam;
import com.gitee.easyopen.ApiRespWriter;
import com.gitee.easyopen.ApiResult;
import com.gitee.easyopen.bean.Consts;
import com.gitee.easyopen.interceptor.ApiInterceptorAdapter;
import com.gitee.easyopen.limit.LimitConfig;
import com.gitee.easyopen.limit.LimitConfigManager;
import com.gitee.easyopen.limit.LimitManager;
import com.gitee.easyopen.limit.LimitType;

/**
 * 限流拦截器
 * @author tanghc
 */
public class LimitInterceptor extends ApiInterceptorAdapter {
    private static final byte STATUS_OPEN = 1;
    
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu)
            throws Exception {
        
        ApiConfig apiConfig = ApiContext.getApiConfig();
        LimitManager limitManager = apiConfig.getLimitManager();
        if(limitManager == null) {
            logger.warn("限流LimitManager为null，请检查配置！");
            return true;
        }
        LimitConfigManager limitConfigManager = limitManager.getLimitConfigManager();
        
        ApiParam param = ApiContext.getApiParam();
        String nameVersion = param.fatchNameVersion();
        
        LimitConfig limitConfig = limitConfigManager.getApiLimitConfig(nameVersion);
        
        // 如果开启了限流
        if(limitConfig.getStatus().byteValue() == STATUS_OPEN) {
            // 限流策略
            if(limitConfig.getLimitType().equals(LimitType.LIMIT.name())) {
                boolean canNext = limitManager.acquire(nameVersion);
                // 可以执行下一步
                if(canNext) {
                    return true;
                } else { // 无法执行下一步，返回错误信息
                    ApiResult result = new ApiResult();
                    result.setCode(limitConfig.getLimitCode());
                    result.setMsg(limitConfig.getLimitMsg());
                    ApiRespWriter.doWriter(response, Consts.CONTENT_TYPE_JSON, JSON.toJSONString(result));
                    return false;
                }
            } else { // 令牌桶策略
                double waitSeconds = limitManager.acquireToken(nameVersion);
                this.afterAcquireToken(waitSeconds);
                return true;
            }
            
        } else {
            return true;
        }
        
    }
    
    /**
     * 成功获取到令牌后
     * @param waitSeconds 获取令牌所需要的时间
     */
    protected void afterAcquireToken(double waitSeconds) {
        
    }
    
}
