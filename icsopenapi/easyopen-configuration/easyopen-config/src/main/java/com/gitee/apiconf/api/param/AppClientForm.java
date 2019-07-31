package com.gitee.apiconf.api.param;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;

import org.hibernate.validator.constraints.Length;

import java.util.List;

public class AppClientForm extends AppParam {
	private Long id;
	/** appKey, 数据库字段：app_key */
	@NotBlank(message = "appKey不能为空")
	@Length(max = 100,message = "appKey长度不能超过100")
    private String appKey;

    /** secret, 数据库字段：secret */
	@NotBlank(message = "secret不能为空")
	@Length(max = 100,message = "secret长度不能超过100")
    private String secret;

    /** 公钥, 数据库字段：pub_key */
    private String pubKey;

    /** 私钥, 数据库字段：pri_key */
    private String priKey;

	/** 0启用，1禁用, 数据库字段：status */
	private Byte status;

	@NotEmpty(message = "角色不能为空")
	private List<String> roleCode;

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public List<String> getRoleCode() {
		return roleCode;
	}

	public void setRoleCode(List<String> roleCode) {
		this.roleCode = roleCode;
	}

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getAppKey() {
		return appKey;
	}

	public void setAppKey(String appKey) {
		this.appKey = appKey;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public String getPubKey() {
		return pubKey;
	}

	public void setPubKey(String pubKey) {
		this.pubKey = pubKey;
	}

	public String getPriKey() {
		return priKey;
	}

	public void setPriKey(String priKey) {
		this.priKey = priKey;
	}

}
