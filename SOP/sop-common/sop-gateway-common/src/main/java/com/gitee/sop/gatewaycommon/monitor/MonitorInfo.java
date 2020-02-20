package com.gitee.sop.gatewaycommon.monitor;

import lombok.Data;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;

/**
 * 每个接口 总调用流量，最大时间，最小时间，总时长，平均时长，调用次数，成功次数，失败次数，错误查看。
 *
 * @author tanghc
 */
@Data
public class MonitorInfo {

    /**
     * 接口名
     */
    private String name;
    /**
     * 版本号
     */
    private String version;
    /**
     * serviceId
     */
    private String serviceId;
    /**
     * 总请求数据量
     */
    private AtomicLong totalRequestDataSize;
    /**
     * 总返回数据量
     */
    private AtomicLong totalResponseDataSize;
    /**
     * 请求耗时最长时间
     */
    private Long maxTime;
    /**
     * 请求耗时最小时间
     */
    private Long minTime;
    /**
     * 总时长
     */
    private AtomicLong totalTime;
    /**
     * 总调用次数
     */
    private AtomicLong totalCount;
    /**
     * 成功次数
     */
    private AtomicLong successCount;
    /**
     * 失败次数（业务主动抛出的异常算作成功，如参数校验，未知的错误算失败）
     */
    private AtomicLong errorCount;
    /**
     * 错误信息
     */
    private List<String> errorMsgList;

    public synchronized void storeMaxTime(long spendTime) {
        if (spendTime > maxTime) {
            maxTime = spendTime;
        }
    }

    public synchronized void storeMinTime(long spendTime) {
        if (minTime == 0 || spendTime < minTime) {
            minTime = spendTime;
        }
    }

    public void addErrorMsg(String errorMsg) {
        if (errorMsg == null || "".equals(errorMsg)) {
            return;
        }
        synchronized (this) {
            if (errorMsgList != null && errorMsgList.size() < 10) {
                errorMsgList.add(errorMsg);
            }
        }
    }

}
