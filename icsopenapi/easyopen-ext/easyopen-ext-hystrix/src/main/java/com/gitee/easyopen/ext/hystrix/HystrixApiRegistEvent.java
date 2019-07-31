package com.gitee.easyopen.ext.hystrix;

import com.gitee.easyopen.ApiMeta;
import com.gitee.easyopen.ApiRegistEvent;
import com.gitee.easyopen.bean.ApiDefinition;
import com.gitee.easyopen.bean.Invocation;
import com.gitee.easyopen.bean.MethodCaller;
import com.gitee.easyopen.ext.hystrix.annotation.ApiHystrix;
import com.gitee.easyopen.ext.hystrix.exception.ApiHystrixException;
import com.netflix.hystrix.HystrixCommand;
import com.netflix.hystrix.HystrixCommandGroupKey;
import com.netflix.hystrix.HystrixCommandKey;
import com.netflix.hystrix.HystrixCommandProperties;
import com.netflix.hystrix.HystrixThreadPoolKey;
import com.netflix.hystrix.HystrixThreadPoolProperties;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tanghc
 */
public class HystrixApiRegistEvent implements ApiRegistEvent {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Override
    public void onSuccess(ApiDefinition apiDefinition) {
        ApiHystrix apiHystrix = apiDefinition.getMethod().getAnnotation(ApiHystrix.class);
        if (apiHystrix == null) {
            return;
        }
        logger.debug("【启用Hystrix熔断降级，method:{}, name:{}, version:{}。groupKey:{}, commandKey:{}】",
                apiDefinition.getMethod()
                , apiDefinition.getName()
                , apiDefinition.getVersion()
                , apiHystrix.groupKey()
                , apiHystrix.commandKey()
        );
        MethodCaller methodCaller = this.createMethodCaller(apiHystrix);
        apiDefinition.setMethodCaller(methodCaller);
    }

    /**
     * 构建方法执行器
     *
     * @param apiHystrix
     * @return
     */
    protected MethodCaller createMethodCaller(ApiHystrix apiHystrix) {
        return new MethodCaller() {
            @Override
            public Object call(Invocation invocation) throws Exception {
                HystrixCommand.Setter setter = buildSetter(apiHystrix, invocation.getApiMeta());
                ApiHystrixCommand command = new ApiHystrixCommand(invocation, setter, apiHystrix.errorCode(), apiHystrix.errorMsg());
                Object result = command.execute();
                if (result instanceof ApiHystrixException) {
                    throw (ApiHystrixException) result;
                }
                return result;
            }
        };
    }

    /**
     * 构建Hystrix设置
     *
     * @param apiHystrix
     * @param apiMeta
     * @return
     */
    protected HystrixCommand.Setter buildSetter(ApiHystrix apiHystrix, ApiMeta apiMeta) {
        String groupKey = apiHystrix.groupKey();
        String commandKey = apiHystrix.commandKey();
        if (StringUtils.isBlank(groupKey)) {
            groupKey = apiMeta.getHandler().getClass().getName();
        }
        if (StringUtils.isBlank(commandKey)) {
            commandKey = apiMeta.getName() + apiMeta.getVersion();
        }
        HystrixCommand.Setter setter = HystrixCommand.Setter
                // groupKey
                .withGroupKey(HystrixCommandGroupKey.Factory.asKey(groupKey))
                //commonKey代表一个依赖抽象,相同的依赖要用相同的commonKey,依赖隔离的根本就是依据commonKey进行隔离
                .andCommandKey(HystrixCommandKey.Factory.asKey(commandKey));

        if (StringUtils.isNotBlank(apiHystrix.threadPoolKey())) {
            setter.andThreadPoolKey(HystrixThreadPoolKey.Factory.asKey(apiHystrix.threadPoolKey()));
        }
        if (apiHystrix.executionTimeoutInMilliseconds() > 0) {
            setter.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionTimeoutInMilliseconds(apiHystrix.executionTimeoutInMilliseconds()));
        }
        if (apiHystrix.executionIsolationStrategy() != ExecutionIsolationStrategy.NONE) {
            setter.andCommandPropertiesDefaults(HystrixCommandProperties.Setter().withExecutionIsolationStrategy(buildExecutionIsolationStrategy(apiHystrix.executionIsolationStrategy())));
        }
        if (apiHystrix.coreSize() > 0) {
            setter.andThreadPoolPropertiesDefaults(HystrixThreadPoolProperties.Setter().withCoreSize(apiHystrix.coreSize()));
        }

        return setter;
    }

    private static HystrixCommandProperties.ExecutionIsolationStrategy buildExecutionIsolationStrategy(ExecutionIsolationStrategy strategy) {
        HystrixCommandProperties.ExecutionIsolationStrategy[] values = HystrixCommandProperties.ExecutionIsolationStrategy.values();
        for (HystrixCommandProperties.ExecutionIsolationStrategy value : values) {
            if (value.name().equals(strategy.name())) {
                return value;
            }
        }
        return HystrixCommandProperties.ExecutionIsolationStrategy.THREAD;
    }


}
