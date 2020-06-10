package com.transcation.layout.wrapper;


import com.transcation.layout.callback.DefaultCallback;
import com.transcation.layout.callback.ICallback;
import com.transcation.layout.callback.IService;
import com.transcation.layout.exception.SkippedException;
import com.transcation.layout.executor.timer.SystemClock;
import com.transcation.layout.worker.DependWrapper;
import com.transcation.layout.worker.ResultState;
import com.transcation.layout.worker.ServicdResult;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对每个worker及callback进行包装，一对一
 *
 * @author zhaobo wrote on 2017-11-19.
 */
public class ServiceInstance<T, V> {
    /**
     * worker将来要处理的param
     */
    private T param;
    private IService<T, V> worker;
    private ICallback<T, V> callback;

    private List<ServiceInstance<?, ?>> nextWrappers;

    private List<DependWrapper> dependWrappers;

    private AtomicInteger state = new AtomicInteger(0);

    private volatile ServicdResult<V> serviceResult = ServicdResult.defaultResult();

    private volatile boolean needCheckNextWrapperResult = true;

    private static final int FINISH = 1;
    private static final int ERROR = 2;
    private static final int WORKING = 3;
    private static final int INIT = 0;

    private ServiceInstance(IService<T, V> worker, T param, ICallback<T, V> callback) {
        if (worker == null) {
            throw new NullPointerException("layout .worker is null");
        }
        this.worker = worker;
        this.param = param;
        //允许不设置回调
        if (callback == null) {
            callback = new DefaultCallback<>();
        }
        this.callback = callback;
    }

    /**
     * 开始工作
     * fromWrapper代表这次work是由哪个上游wrapper发起的
     */
    private void work(ThreadPoolExecutor poolExecutor, ServiceInstance fromWrapper, long remainTime) {
        long now = SystemClock.now();
        //总的已经超时了，就快速失败，进行下一个
        if (remainTime <= 0) {
            fastFail(INIT, null);
            beginNext(poolExecutor, now, remainTime);
            return;
        }
        //如果自己已经执行过了。
        //可能有多个依赖，其中的一个依赖已经执行完了，并且自己也已开始执行或执行完毕。当另一个依赖执行完毕，又进来该方法时，就不重复处理了
        if (getState() == FINISH || getState() == ERROR) {
            beginNext(poolExecutor, now, remainTime);
            return;
        }

        //如果在执行前需要校验nextWrapper的状态
        if (needCheckNextWrapperResult) {
            //如果自己的next链上有已经出结果或已经开始执行的任务了，自己就不用继续了
            if (!checkNextWrapperResult()) {
                fastFail(INIT, new SkippedException());
                beginNext(poolExecutor, now, remainTime);
                return;
            }
        }

        //如果没有任何依赖，说明自己就是第一批要执行的
        if (dependWrappers == null || dependWrappers.size() == 0) {
            fire();
            beginNext(poolExecutor, now, remainTime);
            return;
        }

        /*如果有前方依赖，存在两种情况
         一种是前面只有一个wrapper。即 A  ->  B
        一种是前面有多个wrapper。A C D ->   B。需要A、C、D都完成了才能轮到B。但是无论是A执行完，还是C执行完，都会去唤醒B。
        所以需要B来做判断，必须A、C、D都完成，自己才能执行 */

        //只有一个依赖
        if (dependWrappers.size() == 1) {
            doDependsOneJob(fromWrapper);
            beginNext(poolExecutor, now, remainTime);
        } else {
            //有多个依赖时
            doDependsJobs(poolExecutor, dependWrappers, fromWrapper, now, remainTime);
        }

    }


    public void work(ThreadPoolExecutor poolExecutor, long remainTime) {
        work(poolExecutor, null, remainTime);
    }

    /**
     * 总控制台超时，停止所有任务
     */
    public void stopNow() {
        if (getState() == INIT || getState() == WORKING) {
            fastFail(getState(), null);
        }
    }

    /**
     * 判断自己下游链路上，是否存在已经出结果的或已经开始执行的
     * 如果没有返回true，如果有返回false
     */
    private boolean checkNextWrapperResult() {
        //如果自己就是最后一个，或者后面有并行的多个，就返回true
        if (nextWrappers == null || nextWrappers.size() != 1) {
            return getState() == INIT;
        }
        ServiceInstance nextWrapper = nextWrappers.get(0);
        boolean state = nextWrapper.getState() == INIT;
        //继续校验自己的next的状态
        return state && nextWrapper.checkNextWrapperResult();
    }

    /**
     * 进行下一个任务
     */
    private void beginNext(ThreadPoolExecutor poolExecutor, long now, long remainTime) {
        //花费的时间
        long costTime = SystemClock.now() - now;
        if (nextWrappers == null) {
            return;
        }
        if (nextWrappers.size() == 1) {
            nextWrappers.get(0).work(poolExecutor, ServiceInstance.this, remainTime - costTime);
            return;
        }
        CompletableFuture[] futures = new CompletableFuture[nextWrappers.size()];
        for (int i = 0; i < nextWrappers.size(); i++) {
            int finalI = i;
            futures[i] = CompletableFuture.runAsync (() -> nextWrappers.get(finalI)
                    .work(poolExecutor, ServiceInstance.this, remainTime - costTime), poolExecutor);
        }
        try {
            CompletableFuture.allOf(futures).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void doDependsOneJob(ServiceInstance dependWrapper) {
        if (ResultState.TIMEOUT == dependWrapper.getserviceResult().getResultState()) {
            serviceResult = defaultResult();
            fastFail(INIT, null);
        } else if (ResultState.EXCEPTION == dependWrapper.getserviceResult().getResultState()) {
            serviceResult = defaultExResult(dependWrapper.getserviceResult().getEx());
            fastFail(INIT, null);
        } else {
            //前面任务正常完毕了，该自己了
            fire();
        }
    }

    private synchronized void doDependsJobs(ThreadPoolExecutor poolExecutor, List<DependWrapper> dependWrappers, ServiceInstance fromWrapper, long now, long remainTime) {
        boolean nowDependIsMust = false;
        //创建必须完成的上游wrapper集合
        Set<DependWrapper> mustWrapper = new HashSet<>();
        for (DependWrapper dependWrapper : dependWrappers) {
            if (dependWrapper.isMust()) {
                mustWrapper.add(dependWrapper);
            }
            if (dependWrapper.getDependWrapper().equals(fromWrapper)) {
                nowDependIsMust = dependWrapper.isMust();
            }
        }

        //如果全部是不必须的条件，那么只要到了这里，就执行自己。
        if (mustWrapper.size() == 0) {
            if (ResultState.TIMEOUT == fromWrapper.getserviceResult().getResultState()) {
                fastFail(INIT, null);
            } else {
                fire();
            }
            beginNext(poolExecutor, now, remainTime);
            return;
        }

        //如果存在需要必须完成的，且fromWrapper不是必须的，就什么也不干
        if (!nowDependIsMust) {
            return;
        }

        //如果fromWrapper是必须的
        boolean existNoFinish = false;
        boolean hasError = false;
        //先判断前面必须要执行的依赖任务的执行结果，如果有任何一个失败，那就不用走action了，直接给自己设置为失败，进行下一步就是了
        for (DependWrapper dependWrapper : mustWrapper) {
            ServiceInstance workerWrapper = dependWrapper.getDependWrapper();
            ServicdResult tempserviceResult = workerWrapper.getserviceResult();
            //为null或者isWorking，说明它依赖的某个任务还没执行到或没执行完
            if (workerWrapper.getState() == INIT || workerWrapper.getState() == WORKING) {
                existNoFinish = true;
                break;
            }
            if (ResultState.TIMEOUT == tempserviceResult.getResultState()) {
                serviceResult = defaultResult();
                hasError = true;
                break;
            }
            if (ResultState.EXCEPTION == tempserviceResult.getResultState()) {
                serviceResult = defaultExResult(workerWrapper.getserviceResult().getEx());
                hasError = true;
                break;
            }

        }
        //只要有失败的
        if (hasError) {
            fastFail(INIT, null);
            beginNext(poolExecutor, now, remainTime);
            return;
        }

        //如果上游都没有失败，分为两种情况，一种是都finish了，一种是有的在working
        //都finish的话
        if (!existNoFinish) {
            //上游都finish了，进行自己
            fire();
            beginNext(poolExecutor, now, remainTime);
            return;
        }
    }

    /**
     * 执行自己的job.具体的执行是在另一个线程里,但判断阻塞超时是在work线程
     */
    private void fire() {
        //阻塞取结果
        serviceResult = workerDoJob();
    }

    /**
     * 快速失败
     */
    private boolean fastFail(int expect, Exception e) {
        //试图将它从expect状态,改成Error
        if (!compareAndSetState(expect, ERROR)) {
            return false;
        }

        //尚未处理过结果
        if (checkIsNullResult()) {
            if (e == null) {
                serviceResult = defaultResult();
            } else {
                serviceResult = defaultExResult(e);
            }
        }

        callback.result(false, param, serviceResult);
        return true;
    }

    /**
     * 具体的单个worker执行任务
     */
    private ServicdResult<V> workerDoJob() {
        //避免重复执行
        if (!checkIsNullResult()) {
            return serviceResult;
        }
        try {
            //如果已经不是init状态了，说明正在被执行或已执行完毕。这一步很重要，可以保证任务不被重复执行
            if (!compareAndSetState(INIT, WORKING)) {
                return serviceResult;
            }

            callback.begin();

            //执行耗时操作
            V resultValue = worker.action(param);

            //如果状态不是在working,说明别的地方已经修改了
            if (!compareAndSetState(WORKING, FINISH)) {
                return serviceResult;
            }

            serviceResult.setResultState(ResultState.SUCCESS);
            serviceResult.setResult(resultValue);
            //回调成功
            callback.result(true, param, serviceResult);

            return serviceResult;
        } catch (Exception e) {
            //避免重复回调
            if (!checkIsNullResult()) {
                return serviceResult;
            }
            fastFail(WORKING, e);
            return serviceResult;
        }
    }

    public ServicdResult<V> getserviceResult() {
        return serviceResult;
    }

    public List<ServiceInstance<?, ?>> getNextWrappers() {
        return nextWrappers;
    }

    public void setParam(T param) {
        this.param = param;
    }

    private boolean checkIsNullResult() {
        return ResultState.DEFAULT == serviceResult.getResultState();
    }

    private void addDepend(ServiceInstance<?, ?> workerWrapper, boolean must) {
        addDepend(new DependWrapper(workerWrapper, must));
    }

    private void addDepend(DependWrapper dependWrapper) {
        if (dependWrappers == null) {
            dependWrappers = new ArrayList<>();
        }
        //如果依赖的是重复的同一个，就不重复添加了
        for (DependWrapper wrapper : dependWrappers) {
            if (wrapper.equals(dependWrapper)) {
                return;
            }
        }
        dependWrappers.add(dependWrapper);
    }

    private void addNext(ServiceInstance<?, ?> workerWrapper) {
        if (nextWrappers == null) {
            nextWrappers = new ArrayList<>();
        }
        //避免添加重复
        for (ServiceInstance wrapper : nextWrappers) {
            if (workerWrapper.equals(wrapper)) {
                return;
            }
        }
        nextWrappers.add(workerWrapper);
    }

    private void addNextWrappers(List<ServiceInstance<?, ?>> wrappers) {
        if (wrappers == null) {
            return;
        }
        for (ServiceInstance<?, ?> wrapper : wrappers) {
            addNext(wrapper);
        }
    }

    private void addDependWrappers(List<DependWrapper> dependWrappers) {
        if (dependWrappers == null) {
            return;
        }
        for (DependWrapper wrapper : dependWrappers) {
            addDepend(wrapper);
        }
    }

    private ServicdResult<V> defaultResult() {
        serviceResult.setResultState(ResultState.TIMEOUT);
        serviceResult.setResult(worker.defaultValue());
        return serviceResult;
    }

    private ServicdResult<V> defaultExResult(Exception ex) {
        serviceResult.setResultState(ResultState.EXCEPTION);
        serviceResult.setResult(worker.defaultValue());
        serviceResult.setEx(ex);
        return serviceResult;
    }


    private int getState() {
        return state.get();
    }

    private boolean compareAndSetState(int expect, int update) {
        return this.state.compareAndSet(expect, update);
    }

    private void setNeedCheckNextWrapperResult(boolean needCheckNextWrapperResult) {
        this.needCheckNextWrapperResult = needCheckNextWrapperResult;
    }

    public static class Builder<W, C> {
        /**
         * worker将来要处理的param
         */
        private W param;
        private IService<W, C> worker;
        private ICallback<W, C> callback;
        /**
         * 自己后面的所有
         */
        private List<ServiceInstance<?, ?>> nextWrappers;
        /**
         * 自己依赖的所有
         */
        private List<DependWrapper> dependWrappers;
        /**
         * 存储强依赖于自己的wrapper集合
         */
        private Set<ServiceInstance<?, ?>> selfIsMustSet;

        private boolean needCheckNextWrapperResult = true;

        public Builder<W, C> worker(IService<W, C> worker) {
            this.worker = worker;
            return this;
        }

        public Builder<W, C> param(W w) {
            this.param = w;
            return this;
        }

        public Builder<W, C> needCheckNextWrapperResult(boolean needCheckNextWrapperResult) {
            this.needCheckNextWrapperResult = needCheckNextWrapperResult;
            return this;
        }

        public Builder<W, C> callback(ICallback<W, C> callback) {
            this.callback = callback;
            return this;
        }

        public Builder<W, C> depend(ServiceInstance<?, ?>... wrappers) {
            if (wrappers == null) {
                return this;
            }
            for (ServiceInstance<?, ?> wrapper : wrappers) {
                depend(wrapper);
            }
            return this;
        }

        public Builder<W, C> depend(ServiceInstance<?, ?> wrapper) {
            return depend(wrapper, true);
        }

        public Builder<W, C> depend(ServiceInstance<?, ?> wrapper, boolean isMust) {
            if (wrapper == null) {
                return this;
            }
            DependWrapper dependWrapper = new DependWrapper(wrapper, isMust);
            if (dependWrappers == null) {
                dependWrappers = new ArrayList<>();
            }
            dependWrappers.add(dependWrapper);
            return this;
        }

        public Builder<W, C> next(ServiceInstance<?, ?> wrapper) {
            return next(wrapper, true);
        }

        public Builder<W, C> next(ServiceInstance<?, ?> wrapper, boolean selfIsMust) {
            if (nextWrappers == null) {
                nextWrappers = new ArrayList<>();
            }
            nextWrappers.add(wrapper);

            //强依赖自己
            if (selfIsMust) {
                if (selfIsMustSet == null) {
                    selfIsMustSet = new HashSet<>();
                }
                selfIsMustSet.add(wrapper);
            }
            return this;
        }

        public Builder<W, C> next(ServiceInstance<?, ?>... wrappers) {
            if (wrappers == null) {
                return this;
            }
            for (ServiceInstance<?, ?> wrapper : wrappers) {
                next(wrapper);
            }
            return this;
        }

        public ServiceInstance<W, C> build() {
            ServiceInstance<W, C> wrapper = new ServiceInstance<>(worker, param, callback);
            wrapper.setNeedCheckNextWrapperResult(needCheckNextWrapperResult);
            if (dependWrappers != null) {
                for (DependWrapper workerWrapper : dependWrappers) {
                    workerWrapper.getDependWrapper().addNext(wrapper);
                    wrapper.addDepend(workerWrapper);
                }
            }
            if (nextWrappers != null) {
                for (ServiceInstance<?, ?> workerWrapper : nextWrappers) {
                    boolean must = false;
                    if (selfIsMustSet != null && selfIsMustSet.contains(workerWrapper)) {
                        must = true;
                    }
                    workerWrapper.addDepend(wrapper, must);
                    wrapper.addNext(workerWrapper);
                }
            }

            return wrapper;
        }

    }
}