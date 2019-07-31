package com.gitee.apiconf.common;

public class ConfigField {
    /**  数据库字段：field_name */
    private String fieldName;

    /**  数据库字段：field_value */
    private String fieldValue;

    public ConfigField(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public String getFieldName() {
        return fieldName;
    }

    public void setFieldName(String fieldName) {
        this.fieldName = fieldName;
    }

    public String getFieldValue() {
        return fieldValue;
    }

    public void setFieldValue(String fieldValue) {
        this.fieldValue = fieldValue;
    }
}
