package com.gitee.sop.adminserver.entity;

import lombok.Data;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：perm_role
 * 备注：角色表
 *
 * @author tanghc
 */
@Table(name = "perm_role")
@Data
public class PermRole {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 角色代码, 数据库字段：role_code */
    private String roleCode;

    /** 角色描述, 数据库字段：description */
    private String description;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
