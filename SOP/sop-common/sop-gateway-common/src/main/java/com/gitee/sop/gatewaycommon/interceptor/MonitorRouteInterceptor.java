package com.gitee.sop.gatewaycommon.interceptor;

import com.gitee.sop.gatewaycommon.bean.ApiConfig;
import com.gitee.sop.gatewaycommon.monitor.MonitorInfo;
import com.gitee.sop.gatewaycommon.monitor.MonitorManager;
import com.gitee.sop.gatewaycommon.param.ApiParam;

import java.util.ArrayList;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

/**
 * SOP默认的拦截器，用于收集监控数据
 *
 * @author tanghc
 */
public class MonitorRouteInterceptor implements RouteInterceptor {

    private ThreadPoolExecutor threadPoolExecutor;

    public MonitorRouteInterceptor(int threadPoolSize) {
        threadPoolExecutor = new ThreadPoolExecutor(threadPoolSize, threadPoolSize,
                0L, TimeUnit.MILLISECONDS,
                new LinkedBlockingQueue<>());
    }

    public MonitorRouteInterceptor() {
        this(4);
    }

    public MonitorRouteInterceptor(ThreadPoolExecutor threadPoolExecutor) {
        this.threadPoolExecutor = threadPoolExecutor;
    }

    @Override
    public void preRoute(RouteInterceptorContext context) {
    }

    @Override
    public void afterRoute(RouteInterceptorContext context) {
        threadPoolExecutor.execute(() -> this.storeRequestInfo(context));
    }

    /**
     * 记录接口调用流量，最大时间，最小时间，总时长，平均时长，调用次数，成功次数，失败次数.
     * 需要考虑并发情况。
     */
    protected void storeRequestInfo(RouteInterceptorContext context) {
        MonitorManager monitorManager = ApiConfig.getInstance().getMonitorManager();
        ApiParam apiParam = context.getApiParam();
        String routeId = apiParam.fetchNameVersion();
        long spendTime = context.getFinishTimeMillis() - context.getBeginTimeMillis();
        // 这步操作是线程安全的，底层调用了ConcurrentHashMap.computeIfAbsent
        MonitorInfo monitorInfo = monitorManager.getMonitorInfo(routeId, (k) -> this.createMonitorInfo(apiParam));

        monitorInfo.storeMaxTime(spendTime);
        monitorInfo.storeMinTime(spendTime);
        monitorInfo.getTotalCount().incrementAndGet();
        monitorInfo.getTotalTime().addAndGet(spendTime);
        monitorInfo.getTotalRequestDataSize().addAndGet(context.getRequestDataSize());
        monitorInfo.getTotalResponseDataSize().addAndGet(context.getResponseDataSize());
        if (context.isSuccessRequest()) {
            monitorInfo.getSuccessCount().incrementAndGet();
        } else {
            monitorInfo.getErrorCount().incrementAndGet();
            String errorMsg = context.getServiceErrorMsg();
            monitorInfo.addErrorMsg(errorMsg);
        }
    }

    private MonitorInfo createMonitorInfo(ApiParam apiParam) {
        MonitorInfo monitorInfo = new MonitorInfo();
        monitorInfo.setName(apiParam.fetchName());
        monitorInfo.setVersion(apiParam.fetchVersion());
        monitorInfo.setServiceId(apiParam.fetchServiceId());
        monitorInfo.setTotalRequestDataSize(new AtomicLong());
        monitorInfo.setTotalResponseDataSize(new AtomicLong());
        monitorInfo.setTotalTime(new AtomicLong());
        monitorInfo.setMaxTime(0L);
        monitorInfo.setMinTime(0L);
        monitorInfo.setSuccessCount(new AtomicLong());
        monitorInfo.setTotalCount(new AtomicLong());
        monitorInfo.setErrorCount(new AtomicLong());
        monitorInfo.setErrorMsgList(new ArrayList<>(10));
        return monitorInfo;
    }


    @Override
    public int getOrder() {
        return -1000;
    }

}
