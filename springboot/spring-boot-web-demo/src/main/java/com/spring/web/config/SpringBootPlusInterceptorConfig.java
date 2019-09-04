

package com.spring.web.config;

import lombok.Data;
import lombok.experimental.Accessors;

import java.io.Serializable;

/**
 * @author liujixiang
 * @date 2019/8/5
 */
@Data
@Accessors(chain = true)
public class SpringBootPlusInterceptorConfig implements Serializable {
    private static final long serialVersionUID = -2738042100246082469L;

    /**
     * JWT拦截器排除路径
     */
    private InterceptorConfig jwtConfig;

    /**
     * TOKEN超时拦截器排除路径
     */
    private InterceptorConfig tokenTimeoutConfig;

    /**
     * 权限拦截器排除路径
     */
    private InterceptorConfig permissionConfig;

    /**
     * 资源拦截器
     */
    private InterceptorConfig resourceConfig;

    /**
     * 上传拦截器
     */
    private InterceptorConfig uploadConfig;

    /**
     * 下载拦截器
     */
    private InterceptorConfig downloadConfig;

    @Data
    public static class InterceptorConfig {

        /**
         * 是否启用
         */
        private boolean enabled;

        /**
         * 排除路径
         */
        private String[] excludePath;

        /**
         * 包含的路径
         */
        private String[] includePath;

    }

}
