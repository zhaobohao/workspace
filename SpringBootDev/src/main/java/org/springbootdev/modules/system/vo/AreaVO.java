
package org.springbootdev.modules.system.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springbootdev.core.tool.node.INode;
import org.springbootdev.modules.system.entity.Area;

import java.util.ArrayList;
import java.util.List;

/**
 * 行政区划视图实体类
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
@ApiModel(value = "AreaVO对象", description = "行政区划")
public class AreaVO extends Area implements INode {
	private static final long serialVersionUID = 234156123468891L;

	/**
	 * 主键ID
	 */
	private Long id;

	/**
	 * 父节点ID
	 */
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
		return Integer.valueOf(isLeaf);
	}
}
