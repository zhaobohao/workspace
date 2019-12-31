
package org.springbootdev.modules.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springbootdev.core.mp.base.TenantEntity;
import org.springbootdev.core.mp.base.TreeEntity;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author zhaobohao
 */
@Data
@TableName("mk_dept")
@ApiModel(value = "Dept对象", description = "Dept对象")
public class Dept implements Serializable, TenantEntity, TreeEntity {

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
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 是否是叶子节点,0是，1不是。
	 */
	@ApiModelProperty(value = "是否是叶子节点,0是，1不是。")
	protected Integer isLeaf;

	/**
	 * 部门名
	 */
	@ApiModelProperty(value = "部门名")
	private String deptName;

	/**
	 * 部门全称
	 */
	@ApiModelProperty(value = "部门全称")
	private String fullName;

	/**
	 * 备注
	 */
	@ApiModelProperty(value = "备注")
	private String remark;

	/**
	 * 是否已删除
	 */
	@TableLogic
	@ApiModelProperty(value = "是否已删除")
	private Integer isDeleted;

}
