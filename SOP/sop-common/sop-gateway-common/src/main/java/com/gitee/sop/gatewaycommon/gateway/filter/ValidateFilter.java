package com.gitee.sop.gatewaycommon.gateway.filter;

import com.gitee.sop.gatewaycommon.exception.ApiException;
import com.gitee.sop.gatewaycommon.gateway.ServerWebExchangeUtil;
import com.gitee.sop.gatewaycommon.param.ApiParam;
import com.gitee.sop.gatewaycommon.param.ParamBuilder;
import com.gitee.sop.gatewaycommon.validate.Validator;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

/**
 * @author tanghc
 */
@Slf4j
public class ValidateFilter implements GlobalFilter, Ordered {

    @Autowired
    private ParamBuilder<ServerWebExchange> paramBuilder;

    @Autowired
    private Validator validator;

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 解析参数
        ApiParam param = paramBuilder.build(exchange);
        ServerWebExchangeUtil.setApiParam(exchange, param);
        // 验证操作，这里有负责验证签名参数
        try {
            validator.validate(param);
        } catch (ApiException e) {
            log.error("验证失败，params:{}", param.toJSONString(), e);
            throw e;
        }
        return chain.filter(exchange);
    }

    @Override
    public int getOrder() {
        // 最优先执行
        return Orders.VALIDATE_FILTER_ORDER;
    }
}
