package com.gitee.easyopen.monitor;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.ApiParam;
import com.gitee.easyopen.HasConfig;
import com.gitee.easyopen.exception.BusinessParamException;
import com.gitee.easyopen.interceptor.ApiInterceptorAdapter;

/**
 * 负责监控的拦截器
 * 
 * @author tanghc
 */
public class MonitorInterceptor extends ApiInterceptorAdapter implements Visitor {

    private static final String START_TIME = MonitorInterceptor.class.getSimpleName() + "_START_TIME";

    private volatile ExecutorService executorService = null;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object serviceObj, Object argu)
            throws Exception {
        this.in(request, serviceObj, argu);
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object serviceObj,
            Object argu, Object result, Exception e) throws Exception {
        this.out(request, serviceObj, argu, result, e);
    }

    @Override
    public void in(HttpServletRequest request, Object serviceObj, Object argu) {
        request.setAttribute(START_TIME, System.currentTimeMillis());
    }

    @Override
    public void out(final HttpServletRequest request, Object serviceObj, final Object argu, final Object result, final Exception e) {
        if(e instanceof BusinessParamException) {
            return;
        }
        if (executorService == null) {
            synchronized (MonitorInterceptor.class) {
                if (executorService == null) {
                    executorService = Executors.newFixedThreadPool(ApiContext.getApiConfig().getMonitorExecutorSize());
                }
            }
        }
        final long endTime = System.currentTimeMillis();
        final Long startTime = (Long) request.getAttribute(START_TIME);
        final MonitorStore store = this.getMonitorStore();
        final ApiParam param = ApiContext.getApiParam();
        if(param != null) {
            final ApiParam input = param.clone();
            executorService.execute(new Runnable() {
                @Override
                public void run() {
                    store.stat(input, startTime, endTime, argu, result, e);
                }
            });
        }
    }

    public MonitorStore getMonitorStore() {
        return ApiContext.getApiConfig().getMonitorStore();
    }


}
