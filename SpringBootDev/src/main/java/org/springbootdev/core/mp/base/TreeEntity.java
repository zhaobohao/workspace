package org.springbootdev.core.mp.base;

/**
 * 树形表实体类
 *
 * @author zhaobohao
 */
public interface TreeEntity {
	/**
	 * 父级编号
	 */
	public Long getParentId();
	/**
	 * 本级排序号（升序）
	 */
	public Integer getSort() ;
	/**
	 * 是否叶子节点
	 */
	public Integer getIsLeaf() ;
}

