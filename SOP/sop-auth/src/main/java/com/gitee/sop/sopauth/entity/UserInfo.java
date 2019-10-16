package com.gitee.sop.sopauth.entity;

import com.alibaba.fastjson.annotation.JSONField;
import com.gitee.sop.sopauth.auth.OpenUser;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：user_info
 * 备注：用户信息表
 *
 * @author tanghc
 */
@Table(name = "user_info")
@Data
public class UserInfo implements OpenUser {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /** 用户名, 数据库字段：username */
    private String username;

    /** 密码, 数据库字段：password */
    @JSONField(serialize = false)
    private String password;

    /** 昵称, 数据库字段：nickname */
    private String nickname;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;

    @Override
    public String getUserId() {
        return String.valueOf(id);
    }
}
