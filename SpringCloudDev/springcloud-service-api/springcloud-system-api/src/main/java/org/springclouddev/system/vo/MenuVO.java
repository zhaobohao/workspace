package org.springclouddev.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.springclouddev.core.tool.node.INode;
import org.springclouddev.system.entity.Menu;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图实体类
 *
 * @author zhaobohao
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "MenuVO对象", description = "MenuVO对象")
public class MenuVO extends Menu implements INode {
	private static final long serialVersionUID = 1L;

	/**
	 * 主键ID
	 */
	@JsonSerialize(using= ToStringSerializer.class)
private Long id;

	/**
	 * 父节点ID
	 */
	@JsonSerialize(using= ToStringSerializer.class)
private Long parentId;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	private List<INode> children;

	@Override
	public List<INode> getChildren() {
		if (this.children == null) {
			this.children = new ArrayList<>();
		}
		return this.children;
	}
	/**
	 * 是否是叶子节点
	 */
	protected Integer isLeaf;

	@Override
	public Integer IsLeaf() {
		return null;
	}
	/**
	 * 上级菜单
	 */
	private String parentName;

	/**
	 * 菜单类型
	 */
	private String categoryName;

	/**
	 * 按钮功能
	 */
	private String actionName;

	/**
	 * 是否新窗口打开
	 */
	private String isOpenName;
}
