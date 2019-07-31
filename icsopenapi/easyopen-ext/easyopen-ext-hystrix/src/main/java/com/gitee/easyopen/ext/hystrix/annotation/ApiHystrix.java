package com.gitee.easyopen.ext.hystrix.annotation;

import com.gitee.easyopen.ext.hystrix.ExecutionIsolationStrategy;

import java.lang.annotation.Documented;
import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static com.gitee.easyopen.ext.hystrix.ExecutionIsolationStrategy.NONE;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

/**
 * Hystrix相关配置<br/>
 *  参考文献：
 * <pre>
 * <a href="https://www.jianshu.com/p/b9af028efebb">Hystrix使用入门手册（中文）</a>
 * <a href="http://hot66hot.iteye.com/blog/2155036">Hystrix 使用与分析</a>
 * <a href="https://github.com/Netflix/Hystrix">Github</a>
 * <a href="https://github.com/Netflix/Hystrix/wiki/How-To-Use">How-To-Use</a>
 * </pre>
 *
 * @author tanghc
 */
@Documented
@Retention(RUNTIME)
@Target(METHOD)
public @interface ApiHystrix {

    /**
     * groupKey，不指定默认为类全名
     *
     * @return
     */
    String groupKey() default "";

    /**
     * commandKey，不指定默认为接口名+版本号
     *
     * @return
     */
    String commandKey() default "";

    /**
     * 指定了该属性时有效
     *
     * @return
     */
    String threadPoolKey() default "";

    /**
     * 出错code
     *
     * @return
     */
    String errorCode() default "9";

    /**
     * 出错msg
     *
     * @return
     */
    String errorMsg() default "服务不可用";

    /* ======= HystrixCommandProperties ======= */

    /**
     * HystrixCommandProperties.executionTimeoutInMilliseconds<br />
     * 指定超时时间，毫秒，当大于0时有效
     *
     * @return
     */
    int executionTimeoutInMilliseconds() default 0;

    /**
     * 配置信号量隔离方式，指定为非NONE有效
     *
     * @return
     */
    ExecutionIsolationStrategy executionIsolationStrategy() default NONE;


    /* ======= HystrixThreadPoolProperties ======= */

    /**
     * HystrixThreadPoolProperties.coreSize<br />
     * 指定线程池大小，当大于0时有效
     *
     * @return
     */
    int coreSize() default 0;

    /**
     * HystrixThreadPoolProperties.maxQueueSize<br />
     * 队列大小，当大于0时有效
     *
     * @return
     */
    int maxQueueSize() default 0;

    /**
     * HystrixThreadPoolProperties.queueSizeRejectionThreshold<br />
     * 队列reject阈值，可以动态修改
     * maxQueueSize>0是生效，一般设置为小于
     * maxQueueSizede 的数值，当大于0时有效
     *
     * @return
     */
    int queueSizeRejectionThreshold() default 0;


}
