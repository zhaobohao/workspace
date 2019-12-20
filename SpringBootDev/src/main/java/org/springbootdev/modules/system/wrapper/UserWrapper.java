
package org.springbootdev.modules.system.wrapper;

import org.springbootdev.core.mp.support.BaseEntityWrapper;
import org.springbootdev.core.tool.utils.BeanUtil;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.core.tool.utils.SpringUtil;
import org.springbootdev.modules.system.entity.User;
import org.springbootdev.modules.system.service.IDictService;
import org.springbootdev.modules.system.service.IMenuService;
import org.springbootdev.modules.system.service.IUserService;
import org.springbootdev.modules.system.vo.UserVO;

import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	private static IUserService userService;

	private static IMenuService menuService;

	private static IDictService dictService;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		menuService = SpringUtil.getBean(IMenuService.class);
		dictService = SpringUtil.getBean(IDictService.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<String> roleName = userService.getRoleName(user.getRoleId());
		List<String> deptName = userService.getDeptName(user.getDeptId());
		// 当前将roles更换成menu code,来控制前端的各种资源显示
		userVO.setRoles(menuService.roleTreeKeys(user.getRoleId()));
		//userVO.setRoles(userService.getRoleAlians(user.getRoleId()));
		userVO.setAvatar(user.getAvatar());
		userVO.setIntroduction(user.getIntroduction());
		userVO.setName(user.getName());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(Func.join(deptName));
		String dict = dictService.getValue("sex", Func.toStr(user.getSex()));
		userVO.setSexName(dict);
		return userVO;
	}

}
