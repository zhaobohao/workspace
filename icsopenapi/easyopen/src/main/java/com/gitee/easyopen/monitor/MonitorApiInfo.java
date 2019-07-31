package com.gitee.easyopen.monitor;

import java.io.Serializable;
import java.util.Queue;

import com.gitee.easyopen.ApiContext;
import com.gitee.easyopen.bean.LimitQueue;
import com.gitee.easyopen.bean.Pagable;

/**
 * @author tanghc
 */
public class MonitorApiInfo implements Pagable, Serializable {
    private static final long serialVersionUID = -3054515058567028216L;

    /** 接口名 */
    private String name;
    /** 版本号 */
    private String version;
    /** 访问次数 */
    private long visitCount;
    /** 出错次数 */
    private int errorCount;
    /** 平均访问耗时 */
    private double avgConsumeMilliseconds;
    /** 总耗时 */
    private long sumConsumeMilliseconds;
    /** 最大耗时 */
    private long maxConsumeMilliseconds;
    /** 出错信息 */
    private Queue<String> errors = new LimitQueue<>(ApiContext.getApiConfig().getMonitorErrorQueueSize());
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getVersion() {
        return version;
    }

    public void setVersion(String version) {
        this.version = version;
    }

    public long getVisitCount() {
        return visitCount;
    }

    public void setVisitCount(long visitCount) {
        this.visitCount = visitCount;
    }

    public double getAvgConsumeMilliseconds() {
        return avgConsumeMilliseconds;
    }

    public void setAvgConsumeMilliseconds(double avgConsumeMilliseconds) {
        this.avgConsumeMilliseconds = avgConsumeMilliseconds;
    }

    public long getSumConsumeMilliseconds() {
        return sumConsumeMilliseconds;
    }

    public void setSumConsumeMilliseconds(long sumConsumeMilliseconds) {
        this.sumConsumeMilliseconds = sumConsumeMilliseconds;
    }

    public int getErrorCount() {
        return errorCount;
    }

    public void setErrorCount(int errorCount) {
        this.errorCount = errorCount;
    }

    public long getMaxConsumeMilliseconds() {
        return maxConsumeMilliseconds;
    }

    public void setMaxConsumeMilliseconds(long maxConsumeMilliseconds) {
        this.maxConsumeMilliseconds = maxConsumeMilliseconds;
    }

    public Queue<String> getErrors() {
        return errors;
    }

    public void setErrors(Queue<String> errors) {
        this.errors = errors;
    }
    
}
