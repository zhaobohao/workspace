package org.springclouddev.core.tool.node;

import java.util.List;

/**
 * Created by springcloud.
 *
 * @author zhaobohao
 */
public interface INode {

	/**
	 * 主键
	 *
	 * @return Integer
	 */
	Long getId();

	/**
	 * 父主键
	 *
	 * @return Integer
	 */
	Long getParentId();

	/**
	 * 子孙节点
	 *
	 * @return List
	 */
	List<INode> getChildren();

	/**
	 * 是否是叶子节点，0是，1否
	 * @return
	 */
	Integer IsLeaf();

}
