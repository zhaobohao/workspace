
package org.springbootdev.core.tool.node;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 树型节点类
 *
 * @author zhaobohao
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class TreeNode extends BaseNode {

	private String title;

	private Integer key;

	private Integer value;

}