package com.gitee.sop.adminserver.api.isv.result;

import com.gitee.easyopen.doc.annotation.ApiDocField;
import lombok.Data;

import java.util.Date;

/**
 * @author tanghc
 */
@Data
public class RoleVO {
	@ApiDocField(description = "id")
	private Long id;

	@ApiDocField(description = "角色码")
	private String roleCode;

	@ApiDocField(description = "描述")
	private String description;

	@ApiDocField(description = "创建时间")
	private Date gmtCreate;

	@ApiDocField(description = "修改时间")
	private Date gmtModified;
}
