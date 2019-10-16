package com.gitee.sop.adminserver.bean;

import com.alibaba.fastjson.annotation.JSONField;
import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * @author tanghc
 */
@Data
public class RouteDefinition {
    /**
     * 路由的Id
     */
    @ApiDocField(description = "路由id")
    private String id = "";

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
    @JSONField(serialize = false)
    private List<GatewayPredicateDefinition> predicates = new ArrayList<>();

    /**
     * 路由过滤器集合配置
     */
    @JSONField(serialize = false)
    private List<GatewayFilterDefinition> filters = new ArrayList<>();

    /**
     * 路由规则转发的目标uri
     */
    @ApiDocField(description = "uri")
    private String uri;

    /**
     * uri后面跟的path
     */
    @ApiDocField(description = "path")
    private String path;

    /**
     * 路由执行的顺序
     */
    private int order = 0;

    /**
     * 是否忽略验证，业务参数验证除外
     */
    @ApiDocField(description = "是否忽略验证，业务参数验证除外，1：忽略，0：不忽略")
    private int ignoreValidate;

    /**
     * 状态，0：待审核，1：启用，2：禁用
     */
    @ApiDocField(description = "状态，0：待审核，1：启用，2：禁用")
    private int status = 1;

    /**
     * 合并结果，统一格式输出
     */
    @ApiDocField(description = "合并结果，统一格式输出，1：合并，0：不合并")
    private int mergeResult = 1;

    /**
     * 是否需要授权才能访问
     */
    @ApiDocField(description = "是否需要授权才能访问，1：是，0：否")
    private int permission;

    /**
     * 是否是自定义路由
     */
    @ApiDocField(description = "是否是自定义路由，1：是，0：否")
    private int custom;
}