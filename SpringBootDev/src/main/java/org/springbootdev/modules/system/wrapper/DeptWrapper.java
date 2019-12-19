
package org.springbootdev.modules.system.wrapper;

import org.springbootdev.common.constant.CommonConstant;
import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.node.ForestNodeMerger;
import org.springbootdev.core.tool.node.INode;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.core.tool.utils.SpringUtil;
import org.springbootdev.modules.system.entity.Dept;
import org.springbootdev.modules.system.service.IDeptService;
import org.springbootdev.modules.system.vo.DeptVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 */
public class DeptWrapper extends BaseEntityWrapper<Dept, DeptVO> {

	private static IDeptService deptService;

	static {
		deptService = SpringUtil.getBean(IDeptService.class);
	}

	public static DeptWrapper build() {
		return new DeptWrapper();
	}

	@Override
	public DeptVO entityVO(Dept dept) {
		DeptVO deptVO = BeanUtil.copy(dept, DeptVO.class);
		if (Func.equals(dept.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			deptVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Dept parent = deptService.getById(dept.getParentId());
			deptVO.setParentName(parent.getDeptName());
		}
		return deptVO;
	}

	public List<INode> listNodeVO(List<Dept> list) {
		List<INode> collect = list.stream().map(dept -> BeanUtil.copy(dept, DeptVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
