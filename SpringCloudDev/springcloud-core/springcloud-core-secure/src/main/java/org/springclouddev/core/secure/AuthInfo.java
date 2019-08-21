
package org.springclouddev.core.secure;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

/**
 * AuthInfo
 *
 * @author firewan
 */
@Data
@ApiModel(description = "认证信息")
public class AuthInfo {
	@ApiModelProperty(value = "令牌")
	private String accessToken;
	@ApiModelProperty(value = "令牌类型")
	private String tokenType;
	@ApiModelProperty(value = "刷新令牌")
	private String refreshToken;
	@ApiModelProperty(value = "头像")
	private String avatar = "xxx.png";
	@ApiModelProperty(value = "角色名")
	private String authority;
	@ApiModelProperty(value = "用户名")
	private String userName;
	@ApiModelProperty(value = "账号名")
	private String account;
	@ApiModelProperty(value = "过期时间")
	private long expiresIn;
	@ApiModelProperty(value = "许可证")
	private String license = "free for all ";
}
