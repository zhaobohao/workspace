package com.gitee.sop.gatewaycommon.result;

import org.springframework.web.server.ServerWebExchange;

/**
 * @author tanghc
 */
public interface ResultExecutorForGateway extends ResultExecutor<ServerWebExchange, String> {
}
