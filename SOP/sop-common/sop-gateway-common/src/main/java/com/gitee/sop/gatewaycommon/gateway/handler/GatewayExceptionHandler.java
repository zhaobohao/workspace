package com.gitee.sop.gatewaycommon.gateway.handler;

import com.gitee.sop.gatewaycommon.bean.ApiContext;
import com.gitee.sop.gatewaycommon.gateway.ServerWebExchangeUtil;
import com.gitee.sop.gatewaycommon.gateway.result.GatewayResult;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.result.ResultExecutor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.reactive.error.ErrorWebExceptionHandler;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.codec.HttpMessageWriter;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.util.Assert;
import org.springframework.web.reactive.function.BodyInserters;
import org.springframework.web.reactive.function.server.RequestPredicates;
import org.springframework.web.reactive.function.server.RouterFunctions;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.reactive.function.server.ServerResponse;
import org.springframework.web.reactive.result.view.ViewResolver;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;

/**
 * 统一异常处理
 *
 * @author tanghc
 */
public class GatewayExceptionHandler implements ErrorWebExceptionHandler {

    private static final Logger log = LoggerFactory.getLogger(GatewayExceptionHandler.class);

    @Override
    public Mono<Void> handle(ServerWebExchange exchange, Throwable ex) {
        ResultExecutor<ServerWebExchange, GatewayResult> resultExecutor = ApiContext.getApiConfig().getGatewayResultExecutor();
        GatewayResult errorResult = resultExecutor.buildErrorResult(exchange, ex);
        ApiParam apiParam = ServerWebExchangeUtil.getApiParam(exchange);
        // 错误记录
        log.error("gateway网关报错，params:{}, errorMsg:{}", apiParam, ex.getMessage(), ex);
        // 参考AbstractErrorWebExceptionHandler
        if (exchange.getResponse().isCommitted()) {
            return Mono.error(ex);
        }
        ServerRequest newRequest = ServerRequest.create(exchange, this.messageReaders);
        return RouterFunctions.route(RequestPredicates.all(), (serverRequest) -> this.renderErrorResponse(errorResult)).route(newRequest)
                .switchIfEmpty(Mono.error(ex))
                .flatMap((handler) -> handler.handle(newRequest))
                .flatMap((response) -> write(exchange, response));

    }

    /**
     * MessageReader
     */
    private List<HttpMessageReader<?>> messageReaders = Collections.emptyList();

    /**
     * MessageWriter
     */
    private List<HttpMessageWriter<?>> messageWriters = Collections.emptyList();

    /**
     * ViewResolvers
     */
    private List<ViewResolver> viewResolvers = Collections.emptyList();

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageReaders messageReaders
     */
    public void setMessageReaders(List<HttpMessageReader<?>> messageReaders) {
        Assert.notNull(messageReaders, "'messageReaders' must not be null");
        this.messageReaders = messageReaders;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param viewResolvers viewResolvers
     */
    public void setViewResolvers(List<ViewResolver> viewResolvers) {
        this.viewResolvers = viewResolvers;
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param messageWriters messageWriters
     */
    public void setMessageWriters(List<HttpMessageWriter<?>> messageWriters) {
        Assert.notNull(messageWriters, "'messageWriters' must not be null");
        this.messageWriters = messageWriters;
    }


    /**
     * 参考DefaultErrorWebExceptionHandler
     *
     * @param result 返回结果
     * @return 返回mono
     */
    protected Mono<ServerResponse> renderErrorResponse(GatewayResult result) {
        return ServerResponse
                .status(result.getHttpStatus())
                .contentType(result.getContentType())
                .body(BodyInserters.fromObject(result.getBody()));
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     *
     * @param exchange exchange
     * @param response response
     * @return 返回Mono
     */
    private Mono<? extends Void> write(ServerWebExchange exchange,
                                       ServerResponse response) {
        exchange.getResponse().getHeaders()
                .setContentType(response.headers().getContentType());
        return response.writeTo(exchange, new ResponseContext());
    }

    /**
     * 参考AbstractErrorWebExceptionHandler
     */
    private class ResponseContext implements ServerResponse.Context {

        @Override
        public List<HttpMessageWriter<?>> messageWriters() {
            return GatewayExceptionHandler.this.messageWriters;
        }

        @Override
        public List<ViewResolver> viewResolvers() {
            return GatewayExceptionHandler.this.viewResolvers;
        }

    }

}
