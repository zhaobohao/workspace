package com.gitee.sop.adminserver.entity;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：config_route_limit
 * 备注：路由限流配置
 *
 * @author tanghc
 */
@Table(name = "config_route_limit")
@Data
public class ConfigRouteLimit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Integer id;

    /** 路由id, 数据库字段：route_id */
    private String routeId;

    /** serviceId, 数据库字段：service_id */
    private String serviceId;

    /** 限流策略，1：漏桶策略，2：令牌桶策略, 数据库字段：limit_type */
    private Byte limitType;

    /** 每秒可处理请求数, 数据库字段：exec_count_per_second */
    private Integer execCountPerSecond;

    /** 返回的错误码, 数据库字段：limit_code */
    private String limitCode;

    /** 返回的错误信息, 数据库字段：limit_msg */
    private String limitMsg;

    /** 令牌桶容量, 数据库字段：token_bucket_count */
    private Integer tokenBucketCount;

    /** 1:开启，0关闭, 数据库字段：limit_status */
    private Byte limitStatus;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
