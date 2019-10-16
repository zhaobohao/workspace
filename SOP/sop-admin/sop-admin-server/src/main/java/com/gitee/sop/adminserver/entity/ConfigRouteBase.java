package com.gitee.sop.adminserver.entity;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：config_route_base
 * 备注：路由配置表
 *
 * @author tanghc
 */
@Table(name = "config_route_base")
@Data
public class ConfigRouteBase {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 路由id, 数据库字段：route_id */
    private String routeId;

    /** 状态，1：启用，2：禁用, 数据库字段：status */
    private Byte status;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
