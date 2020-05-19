package com.springclouddev.loges.core.constant;

/**
 * className：LogMessageConstant
 * description：
 * time：2020-05-12.10:10
 *
 * @author Tank
 * @version 1.0.0
 */
public interface LogMessageConstant {

    /**
     * 链路日志前缀
     */
    String TRACE_PRE = "TRACE:";
    /**
     * 当前链路开始标志
     */
    String TRACE_START = "<";

    /**
     * 当前链路结束标志
     */
    String TRACE_END = ">";


    String LOG_KEY = "springclouddev-log-es_list";

    /**
     * 链路日志存入ES的索引后缀
     */
    String LOG_KEY_TRACE = "springclouddev-log-es_list_trace";

    String ES_INDEX = "springclouddev-log-es_";

    String ES_TYPE = "springclouddev-log-es";

    String LOG_TYPE_RUN = "run";

    String LOG_TYPE_TRACE = "trace";
}
