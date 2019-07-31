package com.gitee.easyopen.spring.boot.autoconfigure;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.util.CollectionUtils;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

import com.gitee.easyopen.ApiConfig;
import com.gitee.easyopen.ApiResult;
import com.gitee.easyopen.config.ConfigClient;
import com.gitee.easyopen.interceptor.ApiInterceptor;
import com.gitee.easyopen.limit.ApiLimitConfigLocalManager;
import com.gitee.easyopen.limit.ApiLimitManager;
import com.gitee.easyopen.message.Errors;
import com.gitee.easyopen.support.ApiController;

import reactor.core.publisher.Mono;


/**
 * @author tanghc
 */
@org.springframework.context.annotation.Configuration
@EnableConfigurationProperties(EasyopenProperties.class)
public class EasyopenAutoConfiguration {

    private final EasyopenProperties properties;

    // application.properties中的配置会注入到EasyopenProperties中
    public EasyopenAutoConfiguration(EasyopenProperties properties) {
        this.properties = properties;
    }

    @Bean
    @ConditionalOnMissingBean
    public ApiConfig apiConfig() {
        return new ApiConfig();
    }
    
    /**
     * 跨域过滤器
     *
     * @return
     */
    @Bean
    @ConditionalOnProperty(prefix = "easyopen", name = "cors", havingValue = "true", matchIfMissing = true)
    public CorsFilter corsFilter() {
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", buildConfig());
        return new CorsFilter(source);
    }

    @Bean
    @ConditionalOnMissingBean
    public CorsConfiguration buildConfig() {
        CorsConfiguration corsConfiguration = new CorsConfiguration();
        corsConfiguration.addAllowedOrigin("*");
        corsConfiguration.addAllowedHeader("*");
        corsConfiguration.addAllowedMethod("*");
        corsConfiguration.setAllowCredentials(true);
        return corsConfiguration;
    }

    /**
     * 默认入口
     */
    @Controller
    @RequestMapping("api")
    public class EayopenIndexController extends ApiController {
    	private static final String OPEN_MONO = "true";
    	
        @Autowired
        private ApiConfig config;

        @Autowired(required = false)
        private RedisTemplate redisTemplate;
        
        @RequestMapping("mono")
        @ResponseBody
        public Mono<Object> mono(HttpServletRequest request, HttpServletResponse response) {
        	Object obj;
        	if(OPEN_MONO.equals(properties.getMono())) {
        		obj = this.getWebfluxInvokeTemplate().processInvoke(request, response);
        	} else {
        		obj = ApiResult.error(Errors.NO_PERMISSION);
        	}
            return Mono.just(obj);
        }

        @Override
        protected void initApiConfig(ApiConfig apiConfig) {
            BeanUtils.copyProperties(properties, apiConfig);
            this.initInterceptor(properties, apiConfig);
            this.initOpenClient(properties, apiConfig);
        }

        private void initInterceptor(EasyopenProperties properties, ApiConfig apiConfig) {
            if (!CollectionUtils.isEmpty(properties.getInterceptors())) {
                List<String> interceptors = properties.getInterceptors();
                ApiInterceptor[] apiInterceptor = new ApiInterceptor[interceptors.size()];
                for (int i = 0; i < interceptors.size(); i++) {
                    String interceptorClassName = interceptors.get(i);
                    try {
                        apiInterceptor[i] = (ApiInterceptor)Class.forName(interceptorClassName).newInstance();
                    } catch (Exception e) {
                        logger.error("Class.forName({}).newInstance() error", interceptorClassName, e);
                        throw new RuntimeException(e);
                    }
                }
                apiConfig.setInterceptors(apiInterceptor);
            }
        }

        private void initOpenClient(EasyopenProperties properties, ApiConfig apiConfig) {
            String ip = properties.getConfigServerIp();
            String port = properties.getConfigServerPort();
            if (StringUtils.hasText(ip) && StringUtils.hasText(port)) {
                // appName 应用名称
                // host    配置中心ip
                // port    配置中心端口
                String docUrl = properties.getDocUrl();
                ConfigClient configClient = new ConfigClient(properties.getAppName(), ip, Integer.valueOf(port), docUrl);
                // 如果要使用分布式业务限流，使用下面这句。默认是单机限流
                if (properties.isConfigDistributedLimit()) {
                    if (redisTemplate == null) {
                        throw new NullPointerException("redisTemplate不能为null，是否缺少spring-boot-starter-data-redis依赖");
                    }
                    configClient.setLimitManager(new ApiLimitManager(redisTemplate, new ApiLimitConfigLocalManager()));
                }
                apiConfig.setConfigClient(configClient);
            }
        }

        @Override
        protected ApiConfig newApiConfig() {
            return config;
        }

    }
}
