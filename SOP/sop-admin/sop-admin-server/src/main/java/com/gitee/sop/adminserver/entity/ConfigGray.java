package com.gitee.sop.adminserver.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.util.Date;


/**
 * 表名：config_gray
 * 备注：服务灰度配置
 *
 * @author tanghc
 */
@Table(name = "config_gray")
@Data
public class ConfigGray {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /**  数据库字段：service_id */
    private String serviceId;

    /** 用户key，多个用引文逗号隔开, 数据库字段：user_key_content */
    private String userKeyContent;

    /** 需要灰度的接口，goods.get1.0=1.2，多个用英文逗号隔开, 数据库字段：name_version_content */
    private String nameVersionContent;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_modified */
    private Date gmtModified;
}
