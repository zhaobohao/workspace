package com.gitee.sop.gatewaycommon.gateway.route;

import com.gitee.sop.gatewaycommon.bean.SopConstants;
import com.gitee.sop.gatewaycommon.gateway.ServerWebExchangeUtil;
import com.gitee.sop.gatewaycommon.param.ParamNames;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.util.AntPathMatcher;
import org.springframework.util.CollectionUtils;
import org.springframework.util.PathMatcher;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.server.ServerWebExchange;

import javax.validation.constraints.NotEmpty;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.function.Predicate;

/**
 * 此断言决定执行哪个路由
 *
 * 使用地方：
 * @see com.gitee.sop.gatewaycommon.gateway.route.GatewayRouteCache
 *
 * @author tanghc
 */
@Slf4j
public class NameVersionRoutePredicateFactory extends AbstractRoutePredicateFactory<NameVersionRoutePredicateFactory.Config> {

    private static final String PARAM_KEY = "param";
    private static final String REGEXP_KEY = "regexp";

    private PathMatcher pathMatcher = new AntPathMatcher();

    public NameVersionRoutePredicateFactory() {
        super(Config.class);
    }

    @Override
    public List<String> shortcutFieldOrder() {
        return Arrays.asList(PARAM_KEY, REGEXP_KEY);
    }

    /**
     * config.param为nameVersion，即路由id
     *
     * @param config
     * @return 返回断言
     */
    @Override
    public Predicate<ServerWebExchange> apply(Config config) {

        return exchange -> {
            Map<String, ?> params = ServerWebExchangeUtil.getRequestParams(exchange);
            if (CollectionUtils.isEmpty(params)) {
                return false;
            }
            String nameVersion = config.param;
            Object name = params.get(ParamNames.API_NAME);
            Object version = params.get(ParamNames.VERSION_NAME);
            if (name == null || version == null) {
                return false;
            }
            String key = name.toString() + version.toString();
            // 如果是通配符
            if (nameVersion.contains("{")) {
                boolean match = pathMatcher.match(nameVersion, key);
                if (match) {
                    Map<String, Object> attributes = exchange.getAttributes();
                    attributes.put(SopConstants.REDIRECT_PATH_KEY, key);
                }
                return match;
            } else {
                return key.equals(nameVersion);
            }
        };
    }

    @Validated
    public static class Config {
        @NotEmpty
        private String param;

        private String regexp;

        public String getParam() {
            return param;
        }

        public Config setParam(String param) {
            this.param = param;
            return this;
        }

        public String getRegexp() {
            return regexp;
        }

        public Config setRegexp(String regexp) {
            this.regexp = regexp;
            return this;
        }
    }
}
