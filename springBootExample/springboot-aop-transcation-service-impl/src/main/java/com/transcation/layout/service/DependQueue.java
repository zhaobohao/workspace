package com.transcation.layout.service;


import com.transcation.layout.instance.ServiceInstance;

/**
 * 对依赖的serviceInstance的封装
 * @author zhaobo wrote on 2017-12-20
 * @version 1.0
 */
public class DependQueue {
    private ServiceInstance dependQueue;

    public void setDependQueue(ServiceInstance dependQueue) {
        this.dependQueue = dependQueue;
    }

    private boolean must = true;

    public DependQueue(ServiceInstance dependQueue, boolean must) {
        this.dependQueue = dependQueue;
        this.must = must;
    }

    public DependQueue() {
    }

    public ServiceInstance getDependQueue() {
        return dependQueue;
    }

    public boolean isMust() {
        return must;
    }

    public void setMust(boolean must) {
        this.must = must;
    }

    @Override
    public String toString() {
        return "DependQueue{" +
                "dependQueue=" + dependQueue +
                ", must=" + must +
                '}';
    }
}
