package com.gitee.apiconf.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import com.gitee.fastmybatis.core.support.EasyuiDatagridParam;

import javax.validation.constraints.NotBlank;

public class AppPageParam extends EasyuiDatagridParam {
    @ApiDocField(description = "app", required = true)
    @NotBlank(message = "app不能为null")
    private String app;

    public String getApp() {
        return app;
    }

    public void setApp(String app) {
        this.app = app;
    }
}
