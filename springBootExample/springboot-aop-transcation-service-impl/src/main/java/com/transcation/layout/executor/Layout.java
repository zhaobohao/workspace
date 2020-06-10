package com.transcation.layout.executor;


import com.transcation.layout.callback.DefaultGroupCallback;
import com.transcation.layout.callback.IGroupCallback;
import com.transcation.layout.wrapper.ServiceInstance;

import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.*;
import java.util.stream.Collectors;

/**
 * 类入口，可以根据自己情况调整core线程的数量
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
    public static boolean beginWork(long timeout, ThreadPoolExecutor pool, ServiceInstance... workerWrapper) throws ExecutionException, InterruptedException {
        if(workerWrapper == null || workerWrapper.length == 0) {
            return false;
        }
        List<ServiceInstance> workerWrappers =  Arrays.stream(workerWrapper).collect(Collectors.toList());

        CompletableFuture[] futures = new CompletableFuture[workerWrappers.size()];
        for (int i = 0; i < workerWrappers.size(); i++) {
            ServiceInstance wrapper = workerWrappers.get(i);
            futures[i] = CompletableFuture.runAsync (() -> wrapper.work(pool, timeout), pool);
        }
        try {
            CompletableFuture.allOf(futures).get(timeout, TimeUnit.MILLISECONDS);
            return true;
        } catch (TimeoutException e) {
            Set<ServiceInstance> set = new HashSet<>();
            totalWorkers(workerWrappers, set);
            for (ServiceInstance wrapper : set) {
                wrapper.stopNow();
            }
            return false;
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
    public static void beginWorklayout (long timeout, IGroupCallback groupCallback, ServiceInstance... workerWrapper) {
        if (groupCallback == null) {
            groupCallback = new DefaultGroupCallback();
        }
        IGroupCallback finalGroupCallback = groupCallback;
        CompletableFuture.runAsync (() -> {
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
    private static void totalWorkers(List<ServiceInstance> workerWrappers, Set<ServiceInstance> set) {
        set.addAll(workerWrappers);
        for (ServiceInstance wrapper : workerWrappers) {
            if (wrapper.getNextWrappers() == null) {
                continue;
            }
            List<ServiceInstance> wrappers = wrapper.getNextWrappers();
            totalWorkers(wrappers, set);
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
