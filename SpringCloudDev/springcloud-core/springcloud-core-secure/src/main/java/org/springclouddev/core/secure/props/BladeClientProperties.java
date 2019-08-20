
package org.springclouddev.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * 客户端校验配置
 *
 * @author firewan
 */
@Data
@ConfigurationProperties("blade.secure")
public class BladeClientProperties {

	private final List<ClientSecure> client = new ArrayList<>();

}
