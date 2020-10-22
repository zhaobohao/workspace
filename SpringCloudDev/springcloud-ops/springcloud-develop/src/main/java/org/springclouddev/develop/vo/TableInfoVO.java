package org.springclouddev.develop.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.databind.ser.std.ToStringSerializer;
import lombok.experimental.Accessors;
import org.springclouddev.core.tool.node.INode;
import org.springclouddev.develop.entity.TableInfo;
import lombok.Data;
import lombok.EqualsAndHashCode;
import io.swagger.annotations.ApiModel;

import java.util.ArrayList;
import java.util.List;

/**
 * 视图实体类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@Data
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "TableInfoVO对象", description = "TableInfoVO对象")
@Accessors(chain = true)
public class TableInfoVO extends TableInfo implements INode {

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

}
