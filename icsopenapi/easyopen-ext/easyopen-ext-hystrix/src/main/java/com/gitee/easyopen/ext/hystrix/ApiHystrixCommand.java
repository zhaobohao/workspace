package com.gitee.easyopen.ext.hystrix;

import com.gitee.easyopen.bean.Invocation;
import com.gitee.easyopen.ext.hystrix.exception.ApiHystrixException;
import com.netflix.hystrix.HystrixCommand;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author tanghc
 */
public class ApiHystrixCommand extends HystrixCommand<Object> {
    private static final Logger logger = LoggerFactory.getLogger(ApiHystrixCommand.class);

    private Invocation invocation;
    private String errorCode;
    private String errorMsg;


    public ApiHystrixCommand(Invocation invocation, Setter setter, String errorCode, String errorMsg) {
        super(setter);
        this.invocation = invocation;
        this.errorCode = errorCode;
        this.errorMsg = errorMsg;
    }

    @Override
    protected Object run() throws Exception {
        if (logger.isDebugEnabled()) {
            logger.debug("调用Hystrix，apiName:{},version:{}", invocation.getApiMeta().getName(), invocation.getApiMeta().getVersion());
        }
        return this.invocation.process();
    }

    @Override
    protected Object getFallback() {
        return new ApiHystrixException(errorMsg, errorCode);
    }
}
