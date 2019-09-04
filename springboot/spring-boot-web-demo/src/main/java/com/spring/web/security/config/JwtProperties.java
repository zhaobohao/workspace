

package com.spring.web.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Jwt配置属性
 * </p>
 * @auth geekidea
 * @date 2019-05-22
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring-boot-plus.jwt")
public class JwtProperties {

    /**
     * 密码
     */
//    @Value("spring-boot-plus.jwt.secret:spring-boot-plus")
    private String secret;

    /**
     * 签发人
     */
//    @Value("spring-boot-plus.jwt.issuer:spring-boot-plus")
    private String issuer;

    /**
     * 主题
     */
//    @Value("spring-boot-plus.jwt.subject:spring-boot-plus-jwt")
    private String subject;

    /**
     * 签发的目标
     */
//    @Value("spring-boot-plus.jwt.audience:web")
    private String audience;

    /**
     * token失效时间,默认30分钟
     */
//    @Value("spring-boot-plus.jwt.default.expire.minutes:30")
    private Integer expireMinutes;

}
