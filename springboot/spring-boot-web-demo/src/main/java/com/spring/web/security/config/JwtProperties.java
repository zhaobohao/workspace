

package com.spring.web.security.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * <p>
 *  Jwt配置属性
 * </p>
 * @author zhaobohao
 * @date 2019-05-22
 **/
@Data
@Component
@ConfigurationProperties(prefix = "spring-web-demo.jwt")
public class JwtProperties {

    /**
     * 密码
     */
//    @Value("spring-web-demo.jwt.secret:spring-web-demo")
    private String secret;

    /**
     * 签发人
     */
//    @Value("spring-web-demo.jwt.issuer:spring-web-demo")
    private String issuer;

    /**
     * 主题
     */
//    @Value("spring-web-demo.jwt.subject:spring-web-demo-jwt")
    private String subject;

    /**
     * 签发的目标
     */
//    @Value("spring-web-demo.jwt.audience:web")
    private String audience;

    /**
     * token失效时间,默认30分钟
     */
//    @Value("spring-web-demo.jwt.default.expire.minutes:30")
    private Integer expireMinutes;

}
