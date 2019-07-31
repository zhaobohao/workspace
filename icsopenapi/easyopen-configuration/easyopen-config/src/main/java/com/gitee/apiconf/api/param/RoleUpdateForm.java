package com.gitee.apiconf.api.param;

import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.NotBlank;

public class RoleUpdateForm {
	private Long id;
	@NotBlank(message = "角色码不能为空")
	@Length(max = 50, message = "角色码长度不能超过50")
	private String roleCode;

	@NotBlank(message = "角色描述不能为空")
	@Length(max = 50, message = "角色描述长度不能超过50")
	private String description;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

}
