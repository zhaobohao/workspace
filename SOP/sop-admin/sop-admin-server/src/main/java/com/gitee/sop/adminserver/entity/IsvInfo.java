package com.gitee.sop.adminserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：isv_info
 * 备注：isv信息表
 *
 * @author tanghc
 */
@Table(name = "isv_info")
@Data
public class IsvInfo {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** appKey, 数据库字段：app_key */
    private String appKey;

    /** 不再使用，转移到isv_keys表 */
    private String secret = "";

    /** 公钥,不再使用，转移到isv_keys表 数据库字段：pub_key */
    private String pubKey;

    /** 私钥,不再使用，转移到isv_keys表 数据库字段：pri_key */
    private String priKey;

    /** 1启用，2禁用, 数据库字段：status */
    private Byte status;

    /** 备注，数据库字段：remark */
    private String remark;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
