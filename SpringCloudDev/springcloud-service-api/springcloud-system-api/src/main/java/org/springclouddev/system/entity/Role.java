package org.springclouddev.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springclouddev.core.mp.base.TenantEntity;
import org.springclouddev.core.mp.base.TreeEntity;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author zhaobohao
 */
@Data
@TableName("mk_role")
@ApiModel(value = "Role对象", description = "Role对象")
public class Role implements Serializable, TenantEntity, TreeEntity {

	private static final long serialVersionUID = 1L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	@TableId(value = "id", type = IdType.NONE)
	@JsonSerialize(using= ToStringSerializer.class)
private Long id;

	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	@TableField(fill = FieldFill.INSERT)
	private String tenantId;

	/**
	 * 父主键
	 */
	@ApiModelProperty(value = "父主键")
	@JsonSerialize(using= ToStringSerializer.class)
private Long parentId;

	/**
	 * 角色名
	 */
	@ApiModelProperty(value = "角色名")
	private String roleName;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 角色别名
	 */
	@ApiModelProperty(value = "角色别名")
	private String roleAlias;

	/**
	 * 是否已删除
	 */
	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;
	/**
	 * 是否是叶子节点,0是，1不是。
	 */
	@ApiModelProperty(value = "是否是叶子节点,0是，1不是。")
	protected Integer isLeaf;

}
