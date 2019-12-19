
package org.springbootdev.modules.system.wrapper;

import org.springbootdev.common.constant.CommonConstant;
import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.node.ForestNodeMerger;
import org.springbootdev.core.tool.node.INode;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.core.tool.utils.SpringUtil;
import org.springbootdev.modules.system.entity.Dict;
import org.springbootdev.modules.system.service.IDictService;
import org.springbootdev.modules.system.vo.DictVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 */
public class DictWrapper extends BaseEntityWrapper<Dict, DictVO> {

	private static IDictService dictService;

	static {
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static DictWrapper build() {
		return new DictWrapper();
	}

	@Override
	public DictVO entityVO(Dict dict) {
		DictVO dictVO = BeanUtil.copy(dict, DictVO.class);
		if (Func.equals(dict.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			dictVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Dict parent = dictService.getById(dict.getParentId());
			dictVO.setParentName(parent.getDictValue());
		}
		return dictVO;
	}

	public List<INode> listNodeVO(List<Dict> list) {
		List<INode> collect = list.stream().map(dict -> BeanUtil.copy(dict, DictVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
