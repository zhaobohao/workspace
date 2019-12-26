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
	 * 父级编号
	 */
	public void setParentId(Long parentId);
	/**
	 * 本级排序号（升序）
	 */
	public Integer getSort() ;
	/**
	 * 本级排序号（升序）
	 */
	public void setSort(Integer sort) ;
	/**
	 * 是否叶子节点
	 */
	public Integer getIsLeaf() ;
	/**
	 * 是否叶子节点
	 */
	public void setIsLeaf(Integer isLeaf) ;
}

