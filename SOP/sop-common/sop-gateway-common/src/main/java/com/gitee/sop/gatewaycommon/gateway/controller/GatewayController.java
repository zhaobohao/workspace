package com.gitee.sop.gatewaycommon.gateway.controller;

import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.exception.ApiException;
import com.gitee.sop.gatewaycommon.gateway.ServerWebExchangeUtil;
import com.gitee.sop.gatewaycommon.message.ErrorEnum;
import com.gitee.sop.gatewaycommon.result.ResultExecutor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author tanghc
 */
@RestController
public class GatewayController {

    /**
     * 处理签名错误返回
     *
     * @param exchange exchange
     * @return 返回最终结果
     */
    @RequestMapping("/sop/validateError")
    public Mono<String> validateError(ServerWebExchange exchange) {
        Throwable throwable = ServerWebExchangeUtil.getThrowable(exchange);
        // 合并微服务传递过来的结果，变成最终结果
        ResultExecutor<ServerWebExchange, String> resultExecutor = ApiContext.getApiConfig().getGatewayResultExecutor();
        String gatewayResult = resultExecutor.buildErrorResult(exchange, throwable);
        return Mono.just(gatewayResult);
    }

    @RequestMapping("/sop/unknown")
    public Mono<String> unknown(ServerWebExchange exchange) {
        ApiException exception = ErrorEnum.ISV_INVALID_METHOD.getErrorMeta().getException();
        ResultExecutor<ServerWebExchange, String> resultExecutor = ApiContext.getApiConfig().getGatewayResultExecutor();
        String gatewayResult = resultExecutor.buildErrorResult(exchange, exception);
        return Mono.just(gatewayResult);
    }

}
