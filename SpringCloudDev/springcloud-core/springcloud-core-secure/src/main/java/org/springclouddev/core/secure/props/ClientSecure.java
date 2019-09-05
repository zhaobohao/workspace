
package org.springclouddev.core.secure.props;

import lombok.Data;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端令牌认证信息
 *
 * @author zhaobohao
 */
@Data
public class ClientSecure {

	private String clientId;

	private final List<String> pathPatterns = new ArrayList<>();

}
