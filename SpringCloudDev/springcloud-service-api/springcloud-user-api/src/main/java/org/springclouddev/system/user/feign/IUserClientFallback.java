
package org.springclouddev.system.user.feign;

import org.springclouddev.core.tool.api.R;
import org.springclouddev.system.user.entity.UserInfo;
import org.springframework.stereotype.Component;

/**
 * Feign失败配置
 *
 * @author zhaobohao
 */
@Component
public class IUserClientFallback implements IUserClient {

	@Override
	public R<UserInfo> userInfo(Long userId) {
		return R.fail("未获取到账号信息");
	}

	@Override
	public R<UserInfo> userInfo(String tenantId, String account, String password) {
		return R.fail("未获取到账号信息");
	}
}
