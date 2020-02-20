package com.gitee.sop.gatewaycommon.config;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tanghc
 */
@Data
@ConfigurationProperties(prefix = "sop.api-config")
public class ApiConfigProperties {

    private List<String> i18nModules = new ArrayList<>();

    /**
     * 忽略验证，设置true，则所有接口不会进行签名校验
     */
    private boolean ignoreValidate;

    /**
     * 是否对结果进行合并。<br>
     * 默认情况下是否合并结果由微服务端决定，一旦指定该值，则由该值决定，不管微服务端如何设置。
     */
    private Boolean mergeResult;

    /**
     * 请求超时时间，默认5分钟，即允许在5分钟内重复请求
     */
    private int timeoutSeconds = 300;

    /**
     * 是否开启限流功能
     */
    private boolean openLimit = true;

    /**
     * 显示返回sign
     */
    private boolean showReturnSign = true;

    /**
     * 保存错误信息容器的容量
     */
    private int storeErrorCapacity = 20;
}
