package com.gitee.apiconf.api.param;

import com.gitee.fastmybatis.core.query.Operator;
import com.gitee.fastmybatis.core.query.annotation.Condition;
import com.gitee.fastmybatis.core.support.EasyuiDatagridParam;

import javax.validation.constraints.NotBlank;

public class AppLimitSearchParam extends EasyuiDatagridParam {
    @NotBlank(message = "app不能为null")
    private String app;

    private String name;
    private Byte status;
    private String limitType;

    @Condition(column = "t2.app")
    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }

    @Condition(column = "t2.name", operator = Operator.like)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Condition(column = "t.status")
    public Byte getStatus() {
        return status;
    }

    public void setStatus(Byte status) {
        this.status = status;
    }

    @Condition(column = "t.limit_type")
    public String getLimitType() {
        return limitType;
    }

    public void setLimitType(String limitType) {
        this.limitType = limitType;
    }
}
