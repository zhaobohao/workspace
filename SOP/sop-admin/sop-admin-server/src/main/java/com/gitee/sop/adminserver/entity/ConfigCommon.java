package com.gitee.sop.adminserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：config_common
 * 备注：通用配置表
 *
 * @author tanghc
 */
@Table(name = "config_common")
@Data
public class ConfigCommon {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 配置分组, 数据库字段：config_group */
    private String configGroup;

    /** 配置key, 数据库字段：config_key */
    private String configKey;

    /** 内容, 数据库字段：content */
    private String content;

    /** 备注, 数据库字段：remark */
    private String remark;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
