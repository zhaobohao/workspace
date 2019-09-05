
package org.springclouddev.system.user.feign;

import lombok.AllArgsConstructor;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.system.user.entity.UserInfo;
import org.springclouddev.system.user.service.IUserService;
import org.springframework.web.bind.annotation.GetMapping;
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

}
