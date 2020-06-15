package com.gitee.sop.gatewaycommon.bean;

import lombok.Data;
import org.springframework.cloud.netflix.zuul.filters.Route;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author tanghc
 */
@Data
public class RouteDefinition {
    /**
     * 路由的Id（接口名+版本号），确保此id全局唯一
     */
    private String id;

    /**
     * 接口名
     */
    private String name;

    /**
     * 版本号
     */
    private String version;

    /**
     * 路由断言集合配置
     */
    private List<GatewayPredicateDefinition> predicates = Collections.emptyList();

    /**
     * 路由过滤器集合配置
     */
    private List<GatewayFilterDefinition> filters = Collections.emptyList();

    /**
     * 路由规则转发的目标uri
     */
    private String uri;

    /**
     * uri后面跟的path
     */
    private String path;

    /**
     * 路由执行的顺序
     */
    private int order = 0;

    /**
     * 是否忽略验证，业务参数验证除外
     */
    private int ignoreValidate;

    /**
     * 状态，0：待审核，1：启用，2：禁用
     */
    private int status = 1;

    /**
     * 是否合并结果
     */
    private int mergeResult;

    /**
     * 是否需要授权才能访问
     */
    private int permission;

    /**
     * 是否需要token
     */
    private int needToken;

    /**
     * 是否是兼容模式，即使用了@ApiAbility注解
     */
    private int compatibleMode;

    private Route route;
}