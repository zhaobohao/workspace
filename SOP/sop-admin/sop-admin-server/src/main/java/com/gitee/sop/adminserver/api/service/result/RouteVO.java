package com.gitee.sop.adminserver.api.service.result;

import com.gitee.sop.adminserver.entity.RouteRoleDTO;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * @author tanghc
 */
@Getter
@Setter
public class RouteVO {

    /**  数据库字段：id */
    private String id;

    /**  数据库字段：service_id */
    private String serviceId;

    /** 接口名, 数据库字段：name */
    private String name;

    /** 版本号, 数据库字段：version */
    private String version;

    /** 路由断言（SpringCloudGateway专用）, 数据库字段：predicates */
    private String predicates;

    /** 路由过滤器（SpringCloudGateway专用）, 数据库字段：filters */
    private String filters;

    /** 路由规则转发的目标uri, 数据库字段：uri */
    private String uri;

    /** uri后面跟的path, 数据库字段：path */
    private String path;

    /** 路由执行的顺序, 数据库字段：order */
    private Integer order;

    /** 是否忽略验证，业务参数验证除外, 数据库字段：ignore_validate */
    private Byte ignoreValidate;

    /** 状态，0：待审核，1：启用，2：禁用, 数据库字段：status */
    private Byte status;

    /** 是否合并结果, 数据库字段：merge_result */
    private Byte mergeResult;

    /** 是否需要token, 数据库字段：need_token */
    private Byte needToken;

    /** 是否需要授权才能访问, 数据库字段：permission */
    private Byte permission;

    private List<RouteRoleDTO> roles;
}
