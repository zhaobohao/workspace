package com.gitee.sop.adminserver.service;

import com.gitee.sop.adminserver.bean.ServiceInfo;
import com.gitee.sop.adminserver.bean.ServiceInstance;

import java.util.List;

/**
 * @author tanghc
 */
public interface RegistryService {
    String TIMESTAMP_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获取所有服务列表
     *
     * @param pageNo   当前页码
     * @param pageSize 分页大小
     * @return 返回服务列表
     * @throws Exception 获取失败抛出异常
     */
    List<ServiceInfo> listAllService(int pageNo, int pageSize) throws Exception;

    /**
     * 服务上线
     *
     * @param serviceInstance
     * @throws Exception 服务上线失败抛出异常
     */
    void onlineInstance(ServiceInstance serviceInstance) throws Exception;

    /**
     * 服务下线
     *
     * @param serviceInstance
     * @throws Exception 服务下线失败抛出异常
     */
    void offlineInstance(ServiceInstance serviceInstance) throws Exception;


    /**
     * 设置实例元数据
     *
     * @param serviceInstance 实例
     * @param key key
     * @param value 值
     * @throws Exception 设置实例元数据失败抛出异常
     */
    void setMetadata(ServiceInstance serviceInstance, String key, String value) throws Exception;
}
