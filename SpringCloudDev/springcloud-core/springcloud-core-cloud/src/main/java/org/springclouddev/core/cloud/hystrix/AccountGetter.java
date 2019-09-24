package org.springclouddev.core.cloud.hystrix;


import org.springclouddev.core.secure.SystemUser;
import org.springclouddev.core.secure.utils.SecureUtil;
import org.springclouddev.core.tool.utils.Charsets;
import org.springclouddev.core.tool.utils.UrlUtil;

import javax.servlet.http.HttpServletRequest;

/**
 * 用户信息获取器
 *
 * @author zhaobo
 */
public class AccountGetter implements HystrixAccountGetter {

	@Override
	public String get(HttpServletRequest request) {
		SystemUser account = SecureUtil.getUser();
		if (account == null) {
			return null;
		}
		// 增加用户头, 123[admin]
		String xAccount = String.format("%s[%s]", account.getUserId(), account.getUserName());
		return UrlUtil.encodeURL(xAccount, Charsets.UTF_8);
	}

}
