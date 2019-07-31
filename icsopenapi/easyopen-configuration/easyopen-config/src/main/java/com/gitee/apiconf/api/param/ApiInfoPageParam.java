package com.gitee.apiconf.api.param;

import com.gitee.fastmybatis.core.query.Operator;
import com.gitee.fastmybatis.core.query.annotation.Condition;

public class ApiInfoPageParam extends AppPageParam {
    private Long apiId;
    private String name;

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    @Condition(operator = Operator.like)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
