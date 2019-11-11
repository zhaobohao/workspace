
package org.springclouddev.system.user.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.utils.BeanUtil;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.core.tool.utils.SpringUtil;
import org.springclouddev.system.feign.IDictClient;
import org.springclouddev.system.user.entity.User;
import org.springclouddev.system.user.service.IUserService;
import org.springclouddev.system.user.vo.UserVO;

import java.util.List;

/**
 * 包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 */
public class UserWrapper extends BaseEntityWrapper<User, UserVO> {

	private static IUserService userService;

	private static IDictClient dictClient;

	static {
		userService = SpringUtil.getBean(IUserService.class);
		dictClient = SpringUtil.getBean(IDictClient.class);
	}

	public static UserWrapper build() {
		return new UserWrapper();
	}

	@Override
	public UserVO entityVO(User user) {
		UserVO userVO = BeanUtil.copy(user, UserVO.class);
		List<String> roleName = userService.getRoleName(user.getRoleId());
		List<String> deptName = userService.getDeptName(user.getDeptId());
		userVO.setRoles(userService.getRoleAlians(user.getRoleId()));
		userVO.setAvatar(user.getAvatar());
		userVO.setIntroduction(user.getIntroduction());
		userVO.setName(user.getName());
		userVO.setRoleName(Func.join(roleName));
		userVO.setDeptName(Func.join(deptName));
		R<String> dict = dictClient.getValue("sex", Func.toInt(user.getSex()));
		if (dict.isSuccess()) {
			userVO.setSexName(dict.getData());
		}
		return userVO;
	}

}
