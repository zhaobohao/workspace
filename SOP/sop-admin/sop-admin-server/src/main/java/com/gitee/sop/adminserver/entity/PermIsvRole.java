package com.gitee.sop.adminserver.entity;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：perm_isv_role
 * 备注：isv角色
 *
 * @author tanghc
 */
@Table(name = "perm_isv_role")
@Data
public class PermIsvRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** isv_info表id, 数据库字段：isv_id */
    private Long isvId;

    /** 角色code, 数据库字段：role_code */
    private String roleCode;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
