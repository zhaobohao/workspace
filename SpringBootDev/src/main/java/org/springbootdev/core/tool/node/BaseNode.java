
package org.springbootdev.core.tool.node;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 节点基类
 *
 * @author zhaobohao
 */
@Data
public class BaseNode implements INode {

	/**
	 * 主键ID
	 */
	protected Long id;

	/**
	 * 父节点ID
	 */
	protected Long parentId;

	/**
	 * 子孙节点
	 */
	@JsonInclude(JsonInclude.Include.NON_EMPTY)
	protected List<INode> children = new ArrayList<>();

	/**
	 * 是否是叶子节点
	 */
	protected Integer isLeaf;

	@Override
	public Integer IsLeaf() {
		return null;
	}

}
