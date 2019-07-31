package com.gitee.apiconf.api.param;

import javax.validation.constraints.NotNull;
import java.util.List;

public class AppApiRoleForm extends AppParam{
    @NotNull(message = "apiId不能为空")
    private Long apiId;
    private List<String> roleCodes;

    public Long getApiId() {
        return apiId;
    }

    public void setApiId(Long apiId) {
        this.apiId = apiId;
    }

    public List<String> getRoleCodes() {
        return roleCodes;
    }

    public void setRoleCodes(List<String> roleCodes) {
        this.roleCodes = roleCodes;
    }
}
