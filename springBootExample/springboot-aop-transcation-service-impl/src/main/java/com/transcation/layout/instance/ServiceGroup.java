package com.transcation.layout.instance;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 暂时用不上
 * @author zhaobo wrote on 2017-11-19.
 */
public class ServiceGroup {
    private List<ServiceInstance> workerWrapperList;
    private List<ServiceInstance> beginList;

    public ServiceGroup() {
        workerWrapperList = new ArrayList<>();
    }

    /**
     * 起始任务
     */
    public ServiceGroup begin(ServiceInstance... workerWrappers) {
        if (workerWrappers == null) {
            throw new NullPointerException("workerWrapper cannot be null");
        }
        beginList = Arrays.asList(workerWrappers);
        return this;
    }

    public ServiceGroup then(ServiceInstance... workerWrappers) {
        if (workerWrappers == null) {
            throw new NullPointerException("workerWrapper cannot be null");
        }
        beginList = Arrays.asList(workerWrappers);
        return this;
    }


    public ServiceGroup addWrappers(List<ServiceInstance> workerWrappers) {
        if (workerWrappers == null) {
            throw new NullPointerException("workers cannot be null");
        }
        this.workerWrapperList.addAll(workerWrappers);
        return this;
    }

    public ServiceGroup addWrappers(ServiceInstance... workerWrappers) {
        if (workerWrappers == null) {
            throw new NullPointerException("workers cannot be null");
        }
        return addWrappers(Arrays.asList(workerWrappers));
    }

    /**
     * 返回当前worker的数量，用于决定启用的线程数量
     *
     * @return size
     */
    public int size() {
        synchronized (this) {
            return workerWrapperList.size();
        }
    }

    public List<ServiceInstance> getWorkerWrapperList() {
        return workerWrapperList;
    }
}
