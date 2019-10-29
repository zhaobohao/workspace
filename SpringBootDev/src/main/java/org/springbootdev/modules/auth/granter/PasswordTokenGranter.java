
package org.springbootdev.modules.auth.granter;

import lombok.AllArgsConstructor;
import org.springbootdev.core.tool.utils.DigestUtil;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.modules.auth.enums.SystemUserEnum;
import org.springbootdev.modules.system.entity.UserInfo;
import org.springbootdev.modules.system.service.IUserService;
import org.springframework.stereotype.Component;

/**
 * PasswordTokenGranter
 *
 * @author merryChen
 */
@Component
@AllArgsConstructor
public class PasswordTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "password";

	private IUserService userService;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String tenantId = tokenParameter.getArgs().getStr("tenantId");
		String account = tokenParameter.getArgs().getStr("account");
		String password = tokenParameter.getArgs().getStr("password");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(account, password)) {
			// 获取用户类型
			String userType = tokenParameter.getArgs().getStr("userType");
			// 根据不同用户类型调用对应的接口返回数据，用户可自行拓展
			if (userType.equals(SystemUserEnum.WEB.getName())) {
				userInfo = userService.userInfo(tenantId, account, DigestUtil.encrypt(password));
			} else if (userType.equals(SystemUserEnum.APP.getName())) {
				userInfo = userService.userInfo(tenantId, account, DigestUtil.encrypt(password));
			} else {
				userInfo = userService.userInfo(tenantId, account, DigestUtil.encrypt(password));
			}
		}
		return userInfo;
	}

}
