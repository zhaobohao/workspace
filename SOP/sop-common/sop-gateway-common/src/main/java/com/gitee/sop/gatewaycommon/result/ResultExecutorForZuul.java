package com.gitee.sop.gatewaycommon.result;

import com.netflix.zuul.context.RequestContext;

/**
 * @author tanghc
 */
public interface ResultExecutorForZuul extends ResultExecutor<RequestContext, String> {
}
