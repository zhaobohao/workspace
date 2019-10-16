package com.gitee.sop.adminserver.api.service.result;

import lombok.Data;

import java.util.Date;


/**
 * 表名：config_ip_black
 * 备注：IP黑名单
 *
 * @author tanghc
 */
@Data
public class ConfigIpBlacklistVO {
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
