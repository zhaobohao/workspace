
package org.springclouddev.system.entity;

import com.baomidou.mybatisplus.annotation.*;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import org.springclouddev.core.mp.base.TenantEntity;
import org.springclouddev.core.mp.base.TreeEntity;
import org.springclouddev.core.tool.utils.Func;

import java.io.Serializable;

/**
 * 实体类
 *
 * @author zhaobohao
 */
@Data
@TableName("mk_menu")
@ApiModel(value = "Menu对象", description = "Menu对象")
public class Menu implements Serializable, TenantEntity, TreeEntity {

	private static final long serialVersionUID = 123449871335649873L;

	/**
	 * 主键
	 */
	@ApiModelProperty(value = "主键")
	'@TableId(value = "id", type = IdType.NONE)
	private Long id;


	/**
	 * 租户ID
	 */
	@ApiModelProperty(value = "租户ID")
	@TableField(fill = FieldFill.INSERT)
	private String tenantId;

	/**
	 * 菜单父主键
	 */
	@ApiModelProperty(value = "菜单父主键")
	private Long parentId;

	/**
	 * 是否是叶子节点,0是，1不是。
	 */
	@ApiModelProperty(value = "是否是叶子节点,0是，1不是。")
	protected Integer isLeaf;

	/**
	 * 排序
	 */
	@ApiModelProperty(value = "排序")
	private Integer sort;

	/**
	 * 菜单编号
	 */
	@ApiModelProperty(value = "菜单编号")
	private String code;

	/**
	 * 菜单名称
	 */
	@ApiModelProperty(value = "菜单名称")
	private String name;

	/**
	 * 菜单别名
	 */
	@ApiModelProperty(value = "菜单别名")
	private String alias;

	/**
	 * 请求地址
	 */
	@ApiModelProperty(value = "请求地址")
	private String path;

	/**
	 * 菜单资源
	 */
	@ApiModelProperty(value = "菜单资源")
	private String source;

	/**
	 * 菜单类型
	 */
	@ApiModelProperty(value = "菜单类型")
	private Integer category;

	/**
	 * 操作按钮类型
	 */
	@ApiModelProperty(value = "操作按钮类型")
	private Integer action;

	/**
	 * 是否打开新页面
	 */
	@ApiModelProperty(value = "是否打开新页面")
	private Integer isOpen;

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

	@Override
	public boolean equals(Object obj) {
		if (this == obj) {
			return true;
		}
		if (obj == null) {
			return false;
		}
		Menu other = (Menu) obj;
		if (Func.equals(this.getId(), other.getId())) {
			return true;
		}
		return false;
	}

}
