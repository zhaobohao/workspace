
package org.springclouddev.core.tool.node;

import java.util.List;

/**
 * Created by Blade.
 *
 * @author zhaobohao
 */
public interface INode {

	/**
	 * 主键
	 *
	 * @return Integer
	 */
	Integer getId();

	/**
	 * 父主键
	 *
	 * @return Integer
	 */
	Integer getParentId();

	/**
	 * 子孙节点
	 *
	 * @return List
	 */
	List<INode> getChildren();

}
