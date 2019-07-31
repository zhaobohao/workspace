package com.gitee.apiconf.api.param;

import javax.validation.constraints.NotBlank;

public class RoleParam {
	@NotBlank(message = "角色码不能为空")
	private String roleCode;

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

}
