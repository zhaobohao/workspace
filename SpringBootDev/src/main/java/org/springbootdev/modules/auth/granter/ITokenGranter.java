
package org.springbootdev.modules.auth.granter;


import org.springbootdev.modules.system.entity.UserInfo;

/**
 * 授权认证统一接口.
 *
 * @author merryChen
 */
public interface ITokenGranter {

	/**
	 * 获取用户信息
	 *
	 * @param tokenParameter 授权参数
	 * @return UserInfo
	 */
	UserInfo grant(TokenParameter tokenParameter);

}
