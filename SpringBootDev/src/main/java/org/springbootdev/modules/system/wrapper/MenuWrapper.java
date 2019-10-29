
package org.springbootdev.modules.system.wrapper;

import org.springbootdev.common.constant.CommonConstant;
import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.node.ForestNodeMerger;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.core.tool.utils.SpringUtil;
import org.springbootdev.modules.system.entity.Menu;
import org.springbootdev.modules.system.service.IDictService;
import org.springbootdev.modules.system.service.IMenuService;
import org.springbootdev.modules.system.vo.MenuVO;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author merryChen
 */
public class MenuWrapper extends BaseEntityWrapper<Menu, MenuVO> {

	private static IMenuService menuService;

	private static IDictService dictService;

	static {
		menuService = SpringUtil.getBean(IMenuService.class);
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static MenuWrapper build() {
		return new MenuWrapper();
	}

	@Override
	public MenuVO entityVO(Menu menu) {
		MenuVO menuVO = BeanUtil.copy(menu, MenuVO.class);
		if (Func.equals(menu.getParentId(), CommonConstant.TOP_PARENT_ID)) {
			menuVO.setParentName(CommonConstant.TOP_PARENT_NAME);
		} else {
			Menu parent = menuService.getById(menu.getParentId());
			menuVO.setParentName(parent.getName());
		}
		menuVO.setCategoryName(dictService.getValue("menu_category", Func.toInt(menuVO.getCategory())));
		menuVO.setActionName(dictService.getValue("button_func", Func.toInt(menuVO.getAction())));
		menuVO.setIsOpenName(dictService.getValue("yes_no", Func.toInt(menuVO.getIsOpen())));
		return menuVO;
	}


	public List<MenuVO> listNodeVO(List<Menu> list) {
		List<MenuVO> collect = list.stream().map(menu -> BeanUtil.copy(menu, MenuVO.class)).collect(Collectors.toList());
		return ForestNodeMerger.merge(collect);
	}

}
