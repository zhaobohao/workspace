
package org.springbootdev.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端校验配置
 *
 * @author zhaobohao
 */
@Data
@ConfigurationProperties("springbootdev.secure")
public class ClientProperties {

	private final List<ClientSecure> client = new ArrayList<>();

}
