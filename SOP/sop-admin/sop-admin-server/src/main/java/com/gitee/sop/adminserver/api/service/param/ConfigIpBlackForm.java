package com.gitee.sop.adminserver.api.service.param;

import lombok.Data;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;


/**
 * 表名：config_ip_black
 * 备注：IP黑名单
 *
 * @author tanghc
 */
@Data
public class ConfigIpBlackForm {
    /**  数据库字段：id */
    private Long id;

    /** ip, 数据库字段：ip */
    @NotBlank(message = "不能为空")
    @Length(max = 30, message = "ip长度太长")
    private String ip;

    /** 备注, 数据库字段：remark */
    @Length(max = 100, message = "备注长度太长")
    private String remark;
}
