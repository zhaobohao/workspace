
package org.springbootdev.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * secure放行额外配置
 *
 * @author zhaobohao
 */
@Data
@ConfigurationProperties("springbootdev.secure")
public class SecureProperties {

	private final List<String> excludePatterns = new ArrayList<>();

}
