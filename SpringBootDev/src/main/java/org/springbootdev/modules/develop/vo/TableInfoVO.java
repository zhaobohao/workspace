
package org.springbootdev.modules.develop.vo;

import com.fasterxml.jackson.annotation.JsonInclude;
import io.swagger.annotations.ApiModel;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;
import org.springbootdev.core.tool.node.INode;
import org.springbootdev.modules.develop.entity.TableInfo;

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
		return null;
	}
	/**
	 * 上级菜单
	 */
	private String parentName;

}
