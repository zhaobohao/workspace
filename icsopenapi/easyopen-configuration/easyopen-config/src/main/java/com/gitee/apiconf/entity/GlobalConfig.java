package com.gitee.apiconf.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


/**
 * 表名：global_config
 * 备注：全局配置表
 */
@Table(name = "global_config")
public class GlobalConfig {
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    /**  数据库字段：id */
    private Long id;

    /**  数据库字段：key_name */
    private String keyName;

    /**  数据库字段：field_name */
    private String fieldName;

    /**  数据库字段：field_value */
    private String fieldValue;

    /** 描述, 数据库字段：remark */
    private String remark;

    /**  数据库字段：gmt_create */
    private Date gmtCreate;

    /**  数据库字段：gmt_update */
    private Date gmtUpdate;

    public void setId(Long id) {
        this.id = id;
    }

    public Long getId() {
        return this.id;
    }

    public void setKeyName(String keyName) {
        this.keyName = keyName;
    }

    public String getKeyName() {
        return this.keyName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldName() {
        return this.fieldName;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }

    public String getFieldValue() {
        return this.fieldValue;
    }

    /** 设置描述,数据库字段：global_config.remark */
    public void setRemark(String remark) {
        this.remark = remark;
    }

    /** 获取描述,数据库字段：global_config.remark */
    public String getRemark() {
        return this.remark;
    }

    public void setGmtCreate(Date gmtCreate) {
        this.gmtCreate = gmtCreate;
    }

    public Date getGmtCreate() {
        return this.gmtCreate;
    }

    public void setGmtUpdate(Date gmtUpdate) {
        this.gmtUpdate = gmtUpdate;
    }

    public Date getGmtUpdate() {
        return this.gmtUpdate;
    }

    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = (prime * result) + ((id == null) ? 0 : id.hashCode());

        return result;
    }

    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }

        if (obj == null) {
            return false;
        }

        if (getClass() != obj.getClass()) {
            return false;
        }

        GlobalConfig other = (GlobalConfig) obj;

        if (id == null) {
            if (other.id != null) {
                return false;
            }
        } else if (!id.equals(other.id)) {
            return false;
        }

        return true;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append("GlobalConfig [");
        sb.append("id=").append(id);
        sb.append(", ");
        sb.append("keyName=").append(keyName);
        sb.append(", ");
        sb.append("fieldName=").append(fieldName);
        sb.append(", ");
        sb.append("fieldValue=").append(fieldValue);
        sb.append(", ");
        sb.append("remark=").append(remark);
        sb.append(", ");
        sb.append("gmtCreate=").append(gmtCreate);
        sb.append(", ");
        sb.append("gmtUpdate=").append(gmtUpdate);
        sb.append("]");

        return sb.toString();
    }
}
