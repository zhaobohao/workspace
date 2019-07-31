package com.gitee.easyopen.monitor;

import java.util.List;

import com.gitee.easyopen.ApiParam;
import com.gitee.easyopen.bean.ApiSearch;

/**
 * @author tanghc
 */
public interface MonitorStore {

    /**
     * 清空监控数据
     */
    /**
     * 清空监控数据.如果name不为空，则删除对应的数据。否则删除全部数据
     * 
     * @param name
     * @param version
     */
    void clean(String name, String version);

    /**
     * 统计
     * 
     * @param param
     *            请求参数
     * @param starTimeMillis
     *            开始时间
     * @param endTimeMillis
     *            结束时间
     * @param argu
     *            方法参数
     * @param result
     *            返回结果
     * @param e
     *            异常
     */
    void stat(ApiParam param, long starTimeMillis, long endTimeMillis, Object argu, Object result, Exception e);

    /**
     * 返回总记录数
     * 
     * @param apiSearch
     *            查询对象
     * @return 返回总数
     */
    int getTotal(ApiSearch apiSearch);

    /**
     * 返回结果集
     * 
     * @param apiSearch
     *            查询对象
     * @return 返回结果集
     */
    List<MonitorApiInfo> getList(ApiSearch apiSearch);

    /**
     * 处理错误
     * 
     * @param param
     *            请求参数
     * @param argu
     *            方法参数
     * @param result
     *            返回结果
     * @param e
     * @param t
     */
    void errorHandler(ApiParam param, Object argu, Object result, Exception e, MonitorApiInfo t);

}
