package com.gitee.sop.adminserver.api.service.result;

import lombok.Data;

import java.util.List;

/**
 * 每个接口 总调用流量，最大时间，最小时间，总时长，平均时长，调用次数，成功次数，失败次数，错误查看。
 *
 * @author tanghc
 */
@Data
public class MonitorInfoVO {

    private Integer id;

    private String instanceId;

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
    private Long totalTime;
    /**
     * 总调用次数
     */
    private Long totalCount;
    /**
     * 成功次数
     */
    private Long successCount;
    /**
     * 失败次数（业务主动抛出的异常算作成功，如参数校验，未知的错误算失败）
     */
    private Long errorCount;
    /**
     * 错误信息
     */
    private List<String> errorMsgList;
    /**
     * 总请求数据量
     */
    private Long totalRequestDataSize;
    /**
     * 总返回数据量
     */
    private Long totalResponseDataSize;
    /**
     * 实例id
     */
    private List<MonitorInfoVO> children;

    public String getRouteId() {
        return name + version;
    }

    /**
     * 平均时长，总时长/总调用次数
     * @return 返回平均时长
     */
    public long getAvgTime() {
        return totalTime/totalCount;
    }
}
