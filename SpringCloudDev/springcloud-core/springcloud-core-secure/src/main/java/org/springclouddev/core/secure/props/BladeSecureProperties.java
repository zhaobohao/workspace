
package org.springclouddev.core.secure.props;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * secure放行额外配置
 *
 * @author firewan
 */
@Data
@ConfigurationProperties("blade.secure.url")
public class BladeSecureProperties {

	private final List<String> excludePatterns = new ArrayList<>();

}
