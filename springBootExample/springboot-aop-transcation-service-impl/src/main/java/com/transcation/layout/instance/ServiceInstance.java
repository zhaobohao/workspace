package com.transcation.layout.instance;


import com.transcation.layout.callback.DefaultCallback;
import com.transcation.layout.callback.ICallback;
import com.transcation.layout.callback.IService;
import com.transcation.layout.exception.ServiceFailsException;
import com.transcation.layout.exception.SkippedException;
import com.transcation.layout.executor.timer.SystemClock;
import com.transcation.layout.service.DependQueue;
import com.transcation.layout.service.ResultState;
import com.transcation.layout.service.ServiceInstanceResult;
import com.transcation.service.base.BaseServiceContext;
import com.transcation.service.enums.ServiceStatus;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 对每个service及callback进行包装，一对一
 *
 * @author zhaobo wrote on 2017-11-19.
 */
public class ServiceInstance {
    /**
     * worker将来要处理的param
     */
    private BaseServiceContext param;
    private IService service;
    private ICallback callback;

    private List<ServiceInstance> nextInstances;

    private List<DependQueue> dependInstances;

    private AtomicInteger state = new AtomicInteger(0);

    private volatile ServiceInstanceResult<ServiceStatus> serviceResult = ServiceInstanceResult.defaultResult();

    private volatile boolean needCheckNextWrapperResult = true;

    private static final int FINISH = 1;
    private static final int ERROR = 2;
    private static final int WORKING = 3;
    private static final int INIT = 0;

    private ServiceInstance(IService service, BaseServiceContext param, ICallback callback) {
        if (service == null) {
            throw new NullPointerException("layout .service is null");
        }
        this.service = service;
        this.param = param;
        //允许不设置回调
        if (callback == null) {
            callback = new DefaultCallback<>();
        }
        this.callback = callback;
    }

    /**
     * 开始工作
     * fromInstance代表这次work是由哪个上游service发起的
     */
    private void work(ThreadPoolExecutor poolExecutor, ServiceInstance fromInstance, long remainTime) {
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
        if (dependInstances == null || dependInstances.size() == 0) {
            fire();
            beginNext(poolExecutor, now, remainTime);
            return;
        }

        //只有一个依赖
        if (dependInstances.size() == 1) {
            doDependsOneJob(fromInstance);
            beginNext(poolExecutor, now, remainTime);
        } else {
            //有多个依赖时
            doDependsJobs(poolExecutor, dependInstances, fromInstance, now, remainTime);
        }

    }


    public void work(ThreadPoolExecutor poolExecutor, long remainTime) {
        work(poolExecutor, null, remainTime);
    }

    /**
     * 开始冲正交易
     */
    public void refund() {
        this.service.refund(param);
    }

    /**
     * 开始查证交易
     */
    public void check() {
        this.service.check(param);
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
        if (nextInstances == null || nextInstances.size() != 1) {
            return getState() == INIT;
        }
        ServiceInstance nextWrapper = nextInstances.get(0);
        boolean state = nextWrapper.getState() == INIT;
        //继续校验自己的next的状态
        return state && nextWrapper.checkNextWrapperResult();
    }

    /**
     * 进行下一个任务
     */
    private void beginNext(ThreadPoolExecutor poolExecutor, long now, long remainTime) {
        //如果本任务失败了，就不要在往下进行了。
        if (getserviceResult().getResult() != ServiceStatus.SUCCESS) {
            return;
        }
        //花费的时间
        long costTime = SystemClock.now() - now;
        if (nextInstances == null) {
            return;
        }
        if (nextInstances.size() == 1) {
            nextInstances.get(0).work(poolExecutor, ServiceInstance.this, remainTime - costTime);
            return;
        }
        CompletableFuture[] futures = new CompletableFuture[nextInstances.size()];
        for (int i = 0; i < nextInstances.size(); i++) {
            int finalI = i;
            futures[i] = CompletableFuture.runAsync(() -> nextInstances.get(finalI)
                    .work(poolExecutor, ServiceInstance.this, remainTime - costTime), poolExecutor);
        }
        try {
            CompletableFuture.allOf(futures).get();
        } catch (InterruptedException | ExecutionException e) {
            e.printStackTrace();
        }
    }

    private void doDependsOneJob(ServiceInstance dependInstance) {
        if (ResultState.TIMEOUT == dependInstance.getserviceResult().getResultState()) {
            serviceResult = defaultResult();
            fastFail(INIT, null);
        } else if (ResultState.EXCEPTION == dependInstance.getserviceResult().getResultState()) {
            serviceResult = defaultExResult(dependInstance.getserviceResult().getEx());
            fastFail(INIT, null);
        } else {
            //前面任务正常完毕了，该自己了
            fire();
        }
    }

    private synchronized void doDependsJobs(ThreadPoolExecutor poolExecutor, List<DependQueue> dependInstances, ServiceInstance fromInstance, long now, long remainTime) {
        boolean nowDependIsMust = false;
        //创建必须完成的上游serviceInstance集合
        Set<DependQueue> mustWrapper = new HashSet<>();
        for (DependQueue dependInstance : dependInstances) {
            if (dependInstance.isMust()) {
                mustWrapper.add(dependInstance);
            }
            if (dependInstance.getDependQueue().equals(fromInstance)) {
                nowDependIsMust = dependInstance.isMust();
            }
        }

        //如果全部是不必须的条件，那么只要到了这里，就执行自己。
        if (mustWrapper.size() == 0) {
            if (ResultState.TIMEOUT == fromInstance.getserviceResult().getResultState()) {
                fastFail(INIT, null);
            } else {
                fire();
            }
            beginNext(poolExecutor, now, remainTime);
            return;
        }

        //如果存在需要必须完成的，且fromInstance不是必须的，就什么也不干
        if (!nowDependIsMust) {
            return;
        }

        //如果fromInstance是必须的
        boolean existNoFinish = false;
        boolean hasError = false;
        //先判断前面必须要执行的依赖任务的执行结果，如果有任何一个失败，那就不用走action了，直接给自己设置为失败，进行下一步就是了
        for (DependQueue dependInstance : mustWrapper) {
            ServiceInstance service = dependInstance.getDependQueue();
            ServiceInstanceResult tempserviceResult = service.getserviceResult();
            //为null或者isWorking，说明它依赖的某个任务还没执行到或没执行完
            if (service.getState() == INIT || service.getState() == WORKING) {
                existNoFinish = true;
                break;
            }
            if (ResultState.TIMEOUT == tempserviceResult.getResultState()) {
                serviceResult = defaultResult();
                hasError = true;
                break;
            }
            if (ResultState.EXCEPTION == tempserviceResult.getResultState()) {
                serviceResult = defaultExResult(service.getserviceResult().getEx());
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
     * 执行自己的job.具体的执行是在另一个线程里,但判断阻塞超时是在Service线程
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
    private ServiceInstanceResult<ServiceStatus> workerDoJob() {
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
            ServiceStatus resultValue = service.trade(param);

            //如果状态不是在working,说明别的地方已经修改了
            if (!compareAndSetState(WORKING, FINISH)) {
                return serviceResult;
            }
            if (resultValue == ServiceStatus.FAILS) {
                serviceResult.setResultState(ResultState.EXCEPTION);
                serviceResult.setEx(new ServiceFailsException());
            } else if (resultValue == ServiceStatus.TIMEOUT) {
                //serviceResult.setResultState(ResultState.TIMEOUT);
                //serviceResult.setEx(new ServiceTimeoutException());
                // 当前service进行查证方法
                resultValue = service.check(param);
                // 如果查证方法返回DOUBT,主交易流程开始冲正。

                // 如果查证方法返回FAILS,主交易流程正常冲正。
                if (resultValue == ServiceStatus.FAILS || resultValue == ServiceStatus.DOUBT) {
                    serviceResult.setResultState(ResultState.EXCEPTION);
                    serviceResult.setEx(new ServiceFailsException());
                }
                // 如果查证方法返回SUCCESS,主交易流程正常运行。
                else  if (resultValue == ServiceStatus.SUCCESS)
                {
                    serviceResult.setResultState(ResultState.SUCCESS);
                }
            } else {
                serviceResult.setResultState(ResultState.SUCCESS);
            }
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

    public ServiceInstanceResult<ServiceStatus> getserviceResult() {
        return serviceResult;
    }

    public List<ServiceInstance> getNextInstances() {
        return nextInstances;
    }

    public void setParam(BaseServiceContext param) {
        this.param = param;
    }

    private boolean checkIsNullResult() {
        return ResultState.DEFAULT == serviceResult.getResultState();
    }

    private void addDepend(ServiceInstance workerWrapper, boolean must) {
        addDepend(new DependQueue(workerWrapper, must));
    }

    private void addDepend(DependQueue dependInstance) {
        if (dependInstances == null) {
            dependInstances = new ArrayList<>();
        }
        //如果依赖的是重复的同一个，就不重复添加了
        for (DependQueue wrapper : dependInstances) {
            if (wrapper.equals(dependInstance)) {
                return;
            }
        }
        dependInstances.add(dependInstance);
    }

    private void addNext(ServiceInstance workerWrapper) {
        if (nextInstances == null) {
            nextInstances = new ArrayList<>();
        }
        //避免添加重复
        for (ServiceInstance wrapper : nextInstances) {
            if (workerWrapper.equals(wrapper)) {
                return;
            }
        }
        nextInstances.add(workerWrapper);
    }

    private void addNextWrappers(List<ServiceInstance> wrappers) {
        if (wrappers == null) {
            return;
        }
        for (ServiceInstance wrapper : wrappers) {
            addNext(wrapper);
        }
    }

    private void adddependInstances(List<DependQueue> dependInstances) {
        if (dependInstances == null) {
            return;
        }
        for (DependQueue wrapper : dependInstances) {
            addDepend(wrapper);
        }
    }

    private ServiceInstanceResult<ServiceStatus> defaultResult() {
        serviceResult.setResultState(ResultState.TIMEOUT);
        serviceResult.setResult(service.defaultValue());
        return serviceResult;
    }

    private ServiceInstanceResult<ServiceStatus> defaultExResult(Exception ex) {
        serviceResult.setResultState(ResultState.EXCEPTION);
        serviceResult.setResult(service.defaultValue());
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

    public static class Builder {
        /**
         * worker将来要处理的param
         */
        private BaseServiceContext param;
        private IService service;
        private ICallback callback;
        /**
         * 自己后面的所有
         */
        private List<ServiceInstance> nextWrappers;
        /**
         * 自己依赖的所有
         */
        private List<DependQueue> dependInstances;
        /**
         * 存储强依赖于自己的wrapper集合
         */
        private Set<ServiceInstance> selfIsMustSet;

        private boolean needCheckNextWrapperResult = true;

        public Builder service(IService service) {
            this.service = service;
            return this;
        }

        public Builder param(BaseServiceContext w) {
            this.param = w;
            return this;
        }

        public Builder needCheckNextWrapperResult(boolean needCheckNextWrapperResult) {
            this.needCheckNextWrapperResult = needCheckNextWrapperResult;
            return this;
        }

        public Builder callback(ICallback callback) {
            this.callback = callback;
            return this;
        }

        public Builder depend(ServiceInstance... wrappers) {
            if (wrappers == null) {
                return this;
            }
            for (ServiceInstance wrapper : wrappers) {
                depend(wrapper);
            }
            return this;
        }

        public Builder depend(ServiceInstance wrapper) {
            return depend(wrapper, true);
        }

        public Builder depend(ServiceInstance wrapper, boolean isMust) {
            if (wrapper == null) {
                return this;
            }
            DependQueue dependInstance = new DependQueue(wrapper, isMust);
            if (dependInstances == null) {
                dependInstances = new ArrayList<>();
            }
            dependInstances.add(dependInstance);
            return this;
        }

        public Builder next(ServiceInstance wrapper) {
            return next(wrapper, true);
        }

        public Builder next(ServiceInstance wrapper, boolean selfIsMust) {
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

        public Builder next(ServiceInstance... wrappers) {
            if (wrappers == null) {
                return this;
            }
            for (ServiceInstance wrapper : wrappers) {
                next(wrapper);
            }
            return this;
        }

        public ServiceInstance build() {
            ServiceInstance wrapper = new ServiceInstance(service, param, callback);
            wrapper.setNeedCheckNextWrapperResult(needCheckNextWrapperResult);
            if (dependInstances != null) {
                for (DependQueue workerWrapper : dependInstances) {
                    workerWrapper.getDependQueue().addNext(wrapper);
                    wrapper.addDepend(workerWrapper);
                }
            }
            if (nextWrappers != null) {
                for (ServiceInstance workerWrapper : nextWrappers) {
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