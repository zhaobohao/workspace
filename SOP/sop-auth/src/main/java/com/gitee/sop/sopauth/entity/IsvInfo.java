package com.gitee.sop.sopauth.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


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

    /** 1启用，2禁用, 数据库字段：status */
    private Byte status;
}
