package com.gitee.sop.gateway.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：config_gray_instance
 *
 * @author tanghc
 */
@Table(name = "config_gray_instance")
@Data
public class ConfigGrayInstance {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** instance_id, 数据库字段：instance_id */
    private String instanceId;

    /** service_id, 数据库字段：service_id */
    private String serviceId;

    /** 0：禁用，1：启用, 数据库字段：status */
    private Byte status;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
