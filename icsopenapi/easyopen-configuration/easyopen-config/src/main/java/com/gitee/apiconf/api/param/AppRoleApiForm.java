package com.gitee.apiconf.api.param;

import javax.validation.constraints.NotBlank;
import java.util.List;

public class AppRoleApiForm extends AppParam {
    private List<Long> apiIds;

    @NotBlank(message = "角色不能为空")
    private String roleCode;

    public List<Long> getApiIds() {
        return apiIds;
    }

    public void setApiIds(List<Long> apiIds) {
        this.apiIds = apiIds;
    }

    public String getRoleCode() {
        return roleCode;
    }

    public void setRoleCode(String roleCode) {
        this.roleCode = roleCode;
    }
}
