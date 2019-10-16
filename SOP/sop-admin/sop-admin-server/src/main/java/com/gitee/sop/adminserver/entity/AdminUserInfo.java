package com.gitee.sop.adminserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.util.Date;


/**
 * 表名：admin_user_info
 * 备注：后台用户表
 *
 * @author tanghc
 */
@Table(name = "admin_user_info")
@Data
public class AdminUserInfo implements Serializable {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 用户名, 数据库字段：username */
    private String username;

    /** 密码, 数据库字段：password */
    private String password;

    /** 状态，1：启用，2：禁用, 数据库字段：status */
    private Byte status;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
