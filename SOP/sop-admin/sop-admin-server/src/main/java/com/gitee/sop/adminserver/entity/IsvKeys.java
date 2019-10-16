package com.gitee.sop.adminserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：isv_keys
 * 备注：ISV秘钥
 *
 * @author tanghc
 */
@Table(name = "isv_keys")
@Data
public class IsvKeys {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /**  数据库字段：app_key */
    private String appKey;

    /** 1:RSA2,2:MD5, 数据库字段：sign_type */
    private Byte signType;

    /** isv_info.sign_type=2时使用, 数据库字段：secret */
    private String secret;

    /** 秘钥格式，1：PKCS8(JAVA适用)，2：PKCS1(非JAVA适用), 数据库字段：key_format */
    private Byte keyFormat;

    /** 开发者生成的公钥, 数据库字段：public_key_isv */
    private String publicKeyIsv;

    /** 开发者生成的私钥（交给开发者）, 数据库字段：private_key_isv */
    private String privateKeyIsv;

    /** 平台生成的公钥（交给开发者）, 数据库字段：public_key_platform */
    private String publicKeyPlatform;

    /** 平台生成的私钥, 数据库字段：private_key_platform */
    private String privateKeyPlatform;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
