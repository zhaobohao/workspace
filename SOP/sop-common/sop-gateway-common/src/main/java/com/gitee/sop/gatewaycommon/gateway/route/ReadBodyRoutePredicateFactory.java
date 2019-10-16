package com.gitee.sop.gatewaycommon.gateway.route;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.cloud.gateway.handler.AsyncPredicate;
import org.springframework.cloud.gateway.handler.predicate.AbstractRoutePredicateFactory;
import org.springframework.cloud.gateway.handler.predicate.ReadBodyPredicateFactory;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpMethod;
import org.springframework.http.codec.HttpMessageReader;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpRequestDecorator;
import org.springframework.util.MultiValueMap;
import org.springframework.web.reactive.function.server.HandlerStrategies;
import org.springframework.web.reactive.function.server.ServerRequest;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Predicate;

import static org.springframework.cloud.gateway.filter.AdaptCachedBodyGlobalFilter.CACHED_REQUEST_BODY_KEY;

/**
 * 获取请求参数插件，兼容get，post，使用方式：
 *     &#64;Bean
 *     ReadBodyRoutePredicateFactory readBodyRoutePredicateFactory() {
 *         return new ReadBodyRoutePredicateFactory();
 *     }
 *
 * @see org.springframework.cloud.gateway.handler.predicate.ReadBodyPredicateFactory
 * 详见：https://blog.51cto.com/thinklili/2329184
 *
 * 使用地方：
 * @see com.gitee.sop.gatewaycommon.gateway.route.GatewayRouteCache
 *
 * @author tanghc
 */
public class ReadBodyRoutePredicateFactory extends AbstractRoutePredicateFactory<ReadBodyRoutePredicateFactory.Config> {

    protected static final Log LOGGER = LogFactory.getLog(ReadBodyPredicateFactory.class);

    private static final String TEST_ATTRIBUTE = "read_body_predicate_test_attribute";
    private static final String CACHE_REQUEST_BODY_OBJECT_KEY = "cachedRequestBodyObject";
    private static final List<HttpMessageReader<?>> HTTP_MESSAGE_READERS = HandlerStrategies.withDefaults().messageReaders();


    public ReadBodyRoutePredicateFactory() {
        super(ReadBodyRoutePredicateFactory.Config.class);
    }

    @Override
    public AsyncPredicate<ServerWebExchange> applyAsync(Config config) {
        return exchange -> {
            HttpMethod method = exchange.getRequest().getMethod();
            if (method == HttpMethod.POST) {
                return this.applyForPost(exchange, config);
            } else {
                return this.applyForGet(exchange, config);
            }
        };
    }

    /**
     * 获取post表单参数
     * @param exchange
     * @param config
     * @return
     */
    protected Mono<Boolean> applyForPost(ServerWebExchange exchange, Config config) {
        Class inClass = config.getInClass();

        Object cachedBody = exchange.getAttribute(CACHE_REQUEST_BODY_OBJECT_KEY);
        // We can only read the body from the request once, once that happens if we try to read the body again an
        // exception will be thrown.  The below if/else caches the body object as a request attribute in the ServerWebExchange
        // so if this filter is run more than once (due to more than one route using it) we do not try to read the
        // request body multiple times
        if (cachedBody != null) {
            try {
                boolean test = config.getPredicate().test(cachedBody);
                exchange.getAttributes().put(TEST_ATTRIBUTE, test);
                return Mono.just(test);
            } catch (ClassCastException e) {
                if (LOGGER.isDebugEnabled()) {
                    LOGGER.debug("Predicate test failed because class in predicate does not canVisit the cached body object",
                            e);
                }
            }
            return Mono.just(false);
        } else {
            //Join all the DataBuffers so we have a single DataBuffer for the body
            return DataBufferUtils.join(exchange.getRequest().getBody())
                    .flatMap(dataBuffer -> {
                        //Update the retain counts so we can read the body twice, once to parse into an object
                        //that we can test the predicate against and a second time when the HTTP client sends
                        //the request downstream
                        //Note: if we end up reading the body twice we will run into a problem, but as of right
                        //now there is no good use case for doing this
                        DataBufferUtils.retain(dataBuffer);
                        //Make a slice for each read so each read has its own read/write indexes
                        Flux<DataBuffer> cachedFlux = Flux.defer(() -> Flux.just(dataBuffer.slice(0, dataBuffer.readableByteCount())));

                        ServerHttpRequest mutatedRequest = new ServerHttpRequestDecorator(exchange.getRequest()) {
                            @Override
                            public Flux<DataBuffer> getBody() {
                                return cachedFlux;
                            }
                        };
                        return ServerRequest.create(exchange.mutate().request(mutatedRequest).build(), HTTP_MESSAGE_READERS)
                                .bodyToMono(inClass)
                                .doOnNext(objectValue -> {
                                    exchange.getAttributes().put(CACHE_REQUEST_BODY_OBJECT_KEY, objectValue);
                                    exchange.getAttributes().put(CACHED_REQUEST_BODY_KEY, cachedFlux);
                                })
                                .map(objectValue -> config.getPredicate().test(objectValue));
                    });

        }
    }

    /**
     * 获取GET请求参数
     * @param exchange
     * @param config
     * @return
     */
    protected Mono<Boolean> applyForGet(ServerWebExchange exchange, Config config) {
        // 处理get请求
        MultiValueMap<String, String> queryParams = exchange.getRequest().getQueryParams();
        String objectValue = null;
        if (queryParams != null && queryParams.size() > 0) {
            List<String> params = new ArrayList<>(queryParams.size());
            for (Map.Entry<String, List<String>> entry : queryParams.entrySet()) {
                params.add(entry.getKey() + "=" + entry.getValue().get(0));
            }
            objectValue = StringUtils.join(params.toArray());
            exchange.getAttributes().put(CACHE_REQUEST_BODY_OBJECT_KEY, objectValue);
        }
        return Mono.just(config.getPredicate().test(objectValue));
    }

    @Override
    public Config newConfig() {
        Config config = super.newConfig();
        config.setInClass(String.class);
        config.setPredicate(Objects::nonNull);
        return config;
    }

    @Override
    @SuppressWarnings("unchecked")
    public Predicate<ServerWebExchange> apply(Config config) {
        throw new UnsupportedOperationException(
                "ReadBodyPredicateFactory is only async.");
    }

    public static class Config {
        private Class inClass;
        private Predicate predicate;
        private Map<String, Object> hints;

        public Class getInClass() {
            return inClass;
        }

        public Config setInClass(Class inClass) {
            this.inClass = inClass;
            return this;
        }

        public Predicate getPredicate() {
            return predicate;
        }

        public <T> Config setPredicate(Class<T> inClass, Predicate<T> predicate) {
            setInClass(inClass);
            this.predicate = predicate;
            return this;
        }

        public Config setPredicate(Predicate predicate) {
            this.predicate = predicate;
            return this;
        }

        public Map<String, Object> getHints() {
            return hints;
        }

        public Config setHints(Map<String, Object> hints) {
            this.hints = hints;
            return this;
        }
    }

}
