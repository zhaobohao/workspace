package com.gitee.sop.adminserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：config_ip_blacklist
 * 备注：IP黑名单
 *
 * @author tanghc
 */
@Table(name = "config_ip_blacklist")
@Data
public class ConfigIpBlacklist {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** ip, 数据库字段：ip */
    private String ip;

    /** 备注, 数据库字段：remark */
    private String remark;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
