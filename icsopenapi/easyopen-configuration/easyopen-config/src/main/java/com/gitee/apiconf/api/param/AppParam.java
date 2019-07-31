package com.gitee.apiconf.api.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;

import javax.validation.constraints.NotBlank;

public class AppParam {
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
