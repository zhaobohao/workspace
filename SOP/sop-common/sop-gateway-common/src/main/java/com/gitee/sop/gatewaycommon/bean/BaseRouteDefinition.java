package com.gitee.sop.gatewaycommon.bean;

import lombok.Getter;
import lombok.Setter;

/**
 * @author tanghc
 */
@Getter
@Setter
public class BaseRouteDefinition {

    /**
     * 路由的Id
     */
    private String id;
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
     * 是否合并结果
     */
    private int mergeResult;

    /**
     * 接口是否需要授权才能访问
     */
    private int permission;
}
