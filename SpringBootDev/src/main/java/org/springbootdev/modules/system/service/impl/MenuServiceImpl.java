
package org.springbootdev.modules.system.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.AllArgsConstructor;
import org.springbootdev.core.secure.SystemUser;
import org.springbootdev.core.secure.utils.SecureUtil;
import org.springbootdev.core.tool.constant.ToolConstant;
import org.springbootdev.core.tool.node.ForestNodeMerger;
import org.springbootdev.core.tool.support.Kv;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.modules.system.dto.MenuDTO;
import org.springbootdev.modules.system.entity.Menu;
import org.springbootdev.modules.system.entity.RoleMenu;
import org.springbootdev.modules.system.mapper.MenuMapper;
import org.springbootdev.modules.system.service.IMenuService;
import org.springbootdev.modules.system.service.IRoleMenuService;
import org.springbootdev.modules.system.vo.MenuVO;
import org.springbootdev.modules.system.wrapper.MenuWrapper;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

/**
 * 服务实现类
 *
 * @author zhaobohao
 */
@Service
@AllArgsConstructor
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements IMenuService {

	IRoleMenuService roleMenuService;

	@Override
	public IPage<MenuVO> selectMenuPage(IPage<MenuVO> page, MenuVO menu) {
		return page.setRecords(baseMapper.selectMenuPage(page, menu));
	}

	@Override
	public List<MenuVO> routes(String roleId) {
		List<Menu> allMenus = baseMapper.allMenu();
		List<Menu> roleMenus = baseMapper.roleMenu(Func.toIntList(roleId));
		List<Menu> routes = new LinkedList<>(roleMenus);
		roleMenus.forEach(roleMenu -> recursion(allMenus, routes, roleMenu));
		routes.sort(Comparator.comparing(Menu::getSort));
		MenuWrapper menuWrapper = new MenuWrapper();
		List<Menu> collect = routes.stream().filter(x -> Func.equals(x.getCategory(), 1)).collect(Collectors.toList());
		return menuWrapper.listNodeVO(collect);
	}

	public void recursion(List<Menu> allMenus, List<Menu> routes, Menu roleMenu) {
		Optional<Menu> menu = allMenus.stream().filter(x -> Func.equals(x.getId(), roleMenu.getParentId())).findFirst();
		if (menu.isPresent() && !routes.contains(menu.get())) {
			routes.add(menu.get());
			recursion(allMenus, routes, menu.get());
		}
	}

	@Override
	public List<MenuVO> buttons(String roleId) {
		List<Menu> buttons = baseMapper.buttons(Func.toIntList(roleId));
		MenuWrapper menuWrapper = new MenuWrapper();
		return menuWrapper.listNodeVO(buttons);
	}

	@Override
	public List<MenuVO> tree(String parentId) {
		return ForestNodeMerger.merge(baseMapper.tree(parentId));
	}

	@Override
	public List<MenuVO> grantTree(SystemUser user) {
		return ForestNodeMerger.merge(user.getTenantId().equals(ToolConstant.ADMIN_TENANT_ID) ? baseMapper.grantTree() : baseMapper.grantTreeByRole(Func.toIntList(user.getRoleId())));
	}

	@Override
	public List<String> roleTreeKeys(String roleIds) {
		List<RoleMenu> roleMenus = roleMenuService.list(Wrappers.<RoleMenu>query().lambda().in(RoleMenu::getRoleId, Func.toLongList(roleIds)));
		List<String>  menuAlias=list(Wrappers.<Menu>query().lambda().in(Menu::getId,
			roleMenus.stream().map(roleMenu -> Func.toLong(roleMenu.getMenuId())).collect(Collectors.toList())
		)).stream().map(menu -> menu.getAlias()).collect(Collectors.toList());
		return menuAlias;
	}

	@Override
	public List<Kv> authRoutes(SystemUser user) {
		if (Func.isEmpty(user)) {
			return null;
		}
		List<MenuDTO> routes = baseMapper.authRoutes(Func.toIntList(user.getRoleId()));
		List<Kv> list = new ArrayList<>();
		routes.forEach(route -> list.add(Kv.init().set(route.getPath(), Kv.init().set("authority", Func.toStrArray(route.getAlias())))));
		return list;
	}

	@Override
	public boolean submit(Menu menu) {
		if(null==menu.getId())
		{
			menu.setIsLeaf(0);
			//维护父组件的isleaf字段
			Menu parent=new Menu();
			parent.setId(menu.getParentId());
			parent.setIsLeaf(1);
			updateById(parent);
		}
		return saveOrUpdate(menu);
	}

}
