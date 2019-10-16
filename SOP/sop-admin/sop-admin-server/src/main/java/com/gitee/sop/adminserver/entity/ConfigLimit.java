package com.gitee.sop.adminserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：config_limit
 * 备注：限流配置
 *
 * @author tanghc
 */
@Table(name = "config_limit")
@Data
public class ConfigLimit {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 路由id, 数据库字段：route_id */
    private String routeId;

    /**  数据库字段：app_key */
    private String appKey;

    /** 限流ip，多个用英文逗号隔开, 数据库字段：limit_ip */
    private String limitIp;

    /** 服务id, 数据库字段：service_id */
    private String serviceId;

    /** 限流策略，1：漏桶策略，2：令牌桶策略, 数据库字段：limit_type */
    private Byte limitType;

    /** 每秒可处理请求数, 数据库字段：exec_count_per_second */
    private Integer execCountPerSecond;

    /** 限流持续时间，默认1秒，即每durationSeconds秒允许多少请求（当limit_type=1时有效）, 数据库字段：durationSeconds */
    private Integer durationSeconds;

    /** 返回的错误码, 数据库字段：limit_code */
    private String limitCode;

    /** 返回的错误信息, 数据库字段：limit_msg */
    private String limitMsg;

    /** 令牌桶容量, 数据库字段：token_bucket_count */
    private Integer tokenBucketCount;

    /** 限流开启状态，1:开启，0关闭, 数据库字段：limit_status */
    private Byte limitStatus;

    /** 顺序，值小的优先执行, 数据库字段：order_index */
    private Integer orderIndex;

    /** 备注, 数据库字段：remark */
    private String remark;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;

}
