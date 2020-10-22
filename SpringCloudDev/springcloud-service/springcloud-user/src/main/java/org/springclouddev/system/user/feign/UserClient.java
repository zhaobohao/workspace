package org.springclouddev.system.user.feign;

import lombok.AllArgsConstructor;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.system.user.entity.User;
import org.springclouddev.system.user.entity.UserInfo;
import org.springclouddev.system.user.entity.UserOauth;
import org.springclouddev.system.user.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户服务Feign实现类
 *
 * @author zhaobohao
 */
@RestController
@AllArgsConstructor
public class UserClient implements IUserClient {

	private IUserService service;

	@Override
	public R<UserInfo> userInfo(Long userId) {
		return R.data(service.userInfo(userId));
	}

	@Override
	@GetMapping(API_PREFIX + "/user-info")
	public R<UserInfo> userInfo(String tenantId, String account, String password) {
		return R.data(service.userInfo(tenantId, account, password));
	}


	@Override
	@PostMapping(API_PREFIX + "/user-auth-info")
	public R<UserInfo> userAuthInfo(UserOauth userOauth) {
		return R.data(service.userInfo(userOauth));
	}

	@Override
	@PostMapping(API_PREFIX + "/save-user")
	public R<Boolean> saveUser(User user) {
		return R.data(service.save(user));
	}


}
