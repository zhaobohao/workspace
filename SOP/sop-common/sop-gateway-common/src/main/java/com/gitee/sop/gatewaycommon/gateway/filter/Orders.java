package com.gitee.sop.gatewaycommon.gateway.filter;

import org.springframework.core.Ordered;

/**
 * @author tanghc
 */
public class Orders {
    /** 验证拦截器order */
    public static final int VALIDATE_FILTER_ORDER = Ordered.HIGHEST_PRECEDENCE + 1000;

    /** 参数格式化过滤器 */
    public static final int PARAMETER_FORMATTER_FILTER_ORDER = VALIDATE_FILTER_ORDER + 1;

    /** 权限验证过滤 */
    public static final int PRE_ROUTE_PERMISSION_FILTER_ORDER = PARAMETER_FORMATTER_FILTER_ORDER + 100;

    /** 验证拦截器order */
    public static final int LIMIT_FILTER_ORDER = PRE_ROUTE_PERMISSION_FILTER_ORDER + 100;

    /** 灰度发布过滤器 */
    public static final int ENV_GRAY_FILTER_ORDER = LIMIT_FILTER_ORDER + 100;

}
