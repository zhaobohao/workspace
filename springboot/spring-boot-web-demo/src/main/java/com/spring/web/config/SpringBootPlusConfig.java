
package com.spring.web.config;


import com.spring.web.core.aop.LogAop;
import com.spring.web.core.web.interceptor.PermissionInterceptor;
import com.spring.web.core.web.interceptor.TokenTimeoutInterceptor;
import com.spring.web.core.web.resource.web.interceptor.DownloadInterceptor;
import com.spring.web.core.web.resource.web.interceptor.ResourceInterceptor;
import com.spring.web.core.web.resource.web.interceptor.UploadInterceptor;
import com.spring.web.security.interceptor.JwtInterceptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * spring-web-demo配置
 * @author zhaobohao
 * @date 2019/8/4
 * @since 1.2.0-RELEASE
 */
@Slf4j
@Configuration
@EnableConfigurationProperties({SpringBootPlusProperties.class})
public class SpringBootPlusConfig {

    /**
     * 配置日志AOP
     * @param springBootPlusProperties
     * @return
     */
    @Bean
    public LogAop logAop(SpringBootPlusProperties springBootPlusProperties){
        LogAop logAop = new LogAop();
        logAop.setRequestLogFormat(springBootPlusProperties.isRequestLogFormat());
        logAop.setResponseLogFormat(springBootPlusProperties.isResponseLogFormat());
        log.info("init LogAop success");
        return logAop;
    }

    /**
     * JWT TOKEN验证拦截器
     * @return
     */
    @Bean
    public JwtInterceptor jwtInterceptor(){
        JwtInterceptor jwtInterceptor = new JwtInterceptor();
        return jwtInterceptor;
    }

    /**
     * 权限拦截器
     * @return
     */
    @Bean
    public PermissionInterceptor permissionInterceptor(){
        PermissionInterceptor permissionInterceptor = new PermissionInterceptor();
        return permissionInterceptor;
    }

    /**
     * TOKEN超时拦截器
     * @return
     */
    @Bean
    public TokenTimeoutInterceptor tokenTimeoutInterceptor(){
        TokenTimeoutInterceptor tokenTimeoutInterceptor = new TokenTimeoutInterceptor();
        return tokenTimeoutInterceptor;
    }

    /**
     * 上传拦截器
     * @return
     */
    @Bean
    public UploadInterceptor uploadInterceptor(){
        UploadInterceptor uploadInterceptor = new UploadInterceptor();
        return uploadInterceptor;
    }

    /**
     * 资源拦截器
     * @return
     */
    @Bean
    public ResourceInterceptor resourceInterceptor(){
        ResourceInterceptor resourceInterceptor = new ResourceInterceptor();
        return resourceInterceptor;
    }

    /**
     * 下载拦截器
     * @return
     */
    @Bean
    public DownloadInterceptor downloadInterceptor(){
        DownloadInterceptor downloadInterceptor = new DownloadInterceptor();
        return downloadInterceptor;
    }
}
