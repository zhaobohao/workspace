package com.gitee.apiconf.entity;

public enum GlobalEnum {
    DEFAULT_LIMIT_TYPE("limit","default_limit_type"),
    DEFAULT_LIMIT_COUNT("limit","default_limit_count"),
    DEFAULT_TOKEN_BUCKET_COUNT("limit","default_token_bucket_count"),
    ;
    private String keyName;
    private String fieldName;

    GlobalEnum(String keyName, String fieldName) {
        this.keyName = keyName;
        this.fieldName = fieldName;
    }

    public String getKeyName() {
        return keyName;
    }

    public String getFieldName() {
        return fieldName;
    }
}
