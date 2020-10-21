package org.springclouddev.iot.base.config;

import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.Duration;

/**
 * Redis Cache
 *

 */
@Configuration
@ConfigurationProperties(prefix = "spring.cache.redis")
public class RedisCacheConfig {
    @Setter
    private Duration timeToLive;

    /**
     * 自定义缓存 Key 生成策略
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator firstKeyGenerator() {
        return (target, method, params) -> params[0].toString();
    }

    /**
     * 自定义缓存 Key 生成策略
     *
     * @return KeyGenerator
     */
    @Bean
    public KeyGenerator commonKeyGenerator() {
        return (target, method, params) -> {
            StringBuilder sb = new StringBuilder();
            sb.append(target.getClass().getName());
            sb.append(".");
            sb.append(method.getName());
            sb.append("#");
            for (Object obj : params) {
                sb.append(obj.toString());
            }
            return sb.toString();
        };
    }
}
