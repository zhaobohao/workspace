package com.transcation.layout.executor;


import com.sun.org.apache.xpath.internal.operations.Bool;
import com.transcation.layout.callback.DefaultGroupCallback;
import com.transcation.layout.callback.IGroupCallback;
import com.transcation.layout.instance.ServiceInstance;
import com.transcation.service.enums.ServiceStatus;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 类入口，可以根据自己情况调整core线程的数量
 *
 * @author zhaobo wrote on 2017-12-18
 * @version 0.0.1
 */
public class Layout {
    public static final ThreadPoolExecutor COMMON_POOL =
            new ThreadPoolExecutor(Runtime.getRuntime().availableProcessors() * 2, 1024,
                    15L, TimeUnit.SECONDS,
                    new LinkedBlockingQueue<>(),
                    (ThreadFactory) Thread::new);

    /**
     * 如果想自定义线程池，请传pool。不自定义的话，就走默认的COMMON_POOL
     */
    public static boolean beginWork(long timeout, ThreadPoolExecutor pool, ServiceInstance... serviceInstancesParams) throws ExecutionException, InterruptedException {
        Boolean ret=Boolean.TRUE;
        if (serviceInstancesParams == null || serviceInstancesParams.length == 0) {
            return Boolean.FALSE;
        }
        List<ServiceInstance> serviceInstances = Arrays.stream(serviceInstancesParams).collect(Collectors.toList());

        CompletableFuture[] futures = new CompletableFuture[serviceInstances.size()];
        for (int i = 0; i < serviceInstances.size(); i++) {
            ServiceInstance service = serviceInstances.get(i);
            futures[i] = CompletableFuture.runAsync(() -> service.work(pool, timeout), pool);
        }
        try {
            CompletableFuture.allOf(futures).get(timeout, TimeUnit.MILLISECONDS);
            // 遍历所有的Service,只要有一个是Fails,所有已执行的service冲正。
            List<ServiceInstance> list = new LinkedList<>();
            totalWorkers(serviceInstances, list);
            int failIndex = 9999999;
            for (int i = list.size()-1; i >=0; i--) {
                if (list.get(i).getserviceResult().getResult() == ServiceStatus.FAILS) {
                    failIndex = i;
                    //  当前失败的service冲正操作
                    list.get(i).refund();
                    ret=Boolean.FALSE;
                }
                if (i < failIndex && list.get(i).getserviceResult().getResult() == ServiceStatus.SUCCESS) {
                    //开始将正向执行成功的service逐步冲正,这里是串行，会耗时。
                    list.get(i).refund();
                }
            }
            // 遍历完成
            return ret;
        } catch (TimeoutException e) {
            List<ServiceInstance> list = new LinkedList<>();
            totalWorkers(serviceInstances, list);
            for (ServiceInstance service : list) {
                service.stopNow();
            }
            return Boolean.FALSE;
        }
    }

    /**
     * 同步阻塞,直到所有都完成,或失败
     */
    public static boolean beginWork(long timeout, ServiceInstance... workerWrapper) throws ExecutionException, InterruptedException {
        return beginWork(timeout, COMMON_POOL, workerWrapper);
    }

    /**
     * 异步执行,直到所有都完成,或失败后，发起回调
     */
    public static void beginWorklayout(long timeout, IGroupCallback groupCallback, ServiceInstance... workerWrapper) {
        if (groupCallback == null) {
            groupCallback = new DefaultGroupCallback();
        }
        IGroupCallback finalGroupCallback = groupCallback;
        CompletableFuture.runAsync(() -> {
            try {
                boolean success = beginWork(timeout, COMMON_POOL, workerWrapper);
                if (success) {
                    finalGroupCallback.success(Arrays.asList(workerWrapper));
                } else {
                    finalGroupCallback.failure(Arrays.asList(workerWrapper), new TimeoutException());
                }
            } catch (ExecutionException | InterruptedException e) {
                e.printStackTrace();
                finalGroupCallback.failure(Arrays.asList(workerWrapper), e);
            }
        });
    }

    /**
     * 总共多少个执行单元
     */
    @SuppressWarnings("unchecked")
    private static void totalWorkers(List<ServiceInstance> workerWrappers, List<ServiceInstance> list) {
        list.addAll(workerWrappers);
        for (ServiceInstance wrapper : workerWrappers) {
            if (wrapper.getNextInstances() == null) {
                continue;
            }
            List<ServiceInstance> wrappers = wrapper.getNextInstances();
            totalWorkers(wrappers, list);
        }

    }


    public static void shutDown() {
        COMMON_POOL.shutdown();
    }

    public static String getThreadCount() {
        return "activeCount=" + COMMON_POOL.getActiveCount() +
                "  completedCount " + COMMON_POOL.getCompletedTaskCount() +
                "  largestCount " + COMMON_POOL.getLargestPoolSize();
    }
}
