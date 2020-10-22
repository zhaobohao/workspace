package org.springclouddev.auth.granter;

import lombok.AllArgsConstructor;
import org.springclouddev.core.secure.exception.SecureException;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.core.tool.utils.SpringUtil;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TokenGranterBuilder
 *
 * @author zhaobohao
 */
@AllArgsConstructor
public class TokenGranterBuilder {

	/**
	 * TokenGranter缓存池
	 */
	private static final Map<String, ITokenGranter> GRANTER_POOL = new ConcurrentHashMap<>();

	static {
		GRANTER_POOL.put(PasswordTokenGranter.GRANT_TYPE, SpringUtil.getBean(PasswordTokenGranter.class));
		GRANTER_POOL.put(CaptchaTokenGranter.GRANT_TYPE, SpringUtil.getBean(CaptchaTokenGranter.class));
		GRANTER_POOL.put(RefreshTokenGranter.GRANT_TYPE, SpringUtil.getBean(RefreshTokenGranter.class));
	}

	/**
	 * 获取TokenGranter
	 *
	 * @param grantType 授权类型
	 * @return ITokenGranter
	 */
	public static ITokenGranter getGranter(String grantType) {
		ITokenGranter tokenGranter = GRANTER_POOL.get(Func.toStr(grantType, PasswordTokenGranter.GRANT_TYPE));
		if (tokenGranter == null) {
			throw new SecureException("no grantType was found");
		} else {
			return tokenGranter;
		}
	}

}
