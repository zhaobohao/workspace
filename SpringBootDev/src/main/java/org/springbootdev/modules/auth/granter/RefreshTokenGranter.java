
package org.springbootdev.modules.auth.granter;

import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import org.springbootdev.core.launch.constant.TokenConstant;
import org.springbootdev.core.secure.utils.SecureUtil;
import org.springbootdev.core.tool.utils.Func;
import org.springbootdev.modules.system.entity.UserInfo;
import org.springbootdev.modules.system.service.IUserService;
import org.springframework.stereotype.Component;

import java.util.Objects;

/**
 * RefreshTokenGranter
 *
 * @author merryChen
 */
@Component
@AllArgsConstructor
public class RefreshTokenGranter implements ITokenGranter {

	public static final String GRANT_TYPE = "refresh_token";

	private IUserService userService;

	@Override
	public UserInfo grant(TokenParameter tokenParameter) {
		String grantType = tokenParameter.getArgs().getStr("grantType");
		String refreshToken = tokenParameter.getArgs().getStr("refreshToken");
		UserInfo userInfo = null;
		if (Func.isNoneBlank(grantType, refreshToken) && grantType.equals(TokenConstant.REFRESH_TOKEN)) {
			Claims claims = SecureUtil.parseJWT(refreshToken);
			String tokenType = Func.toStr(Objects.requireNonNull(claims).get(TokenConstant.TOKEN_TYPE));
			if (tokenType.equals(TokenConstant.REFRESH_TOKEN)) {
				userInfo= userService.userInfo(Func.toLong(claims.get(TokenConstant.USER_ID)));
			}
		}
		return userInfo;
	}
}
