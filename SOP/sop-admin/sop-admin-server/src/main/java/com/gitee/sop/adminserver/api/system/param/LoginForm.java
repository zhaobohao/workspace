package com.gitee.sop.adminserver.api.system.param;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

import javax.validation.constraints.NotBlank;

@Data
public class LoginForm {
    @NotBlank(message = "用户名不能为空")
    @ApiDocField(description = "用户名", required = true)
    private String username;

    @NotBlank(message = "密码不能为空")
    @ApiDocField(description = "密码", required = true)
    private String password;
}