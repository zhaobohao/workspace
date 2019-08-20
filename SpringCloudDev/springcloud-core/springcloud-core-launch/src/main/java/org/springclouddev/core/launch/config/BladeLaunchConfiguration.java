
package org.springclouddev.core.launch.config;

import lombok.AllArgsConstructor;
import org.springclouddev.core.launch.props.BladeProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 *
 * @author firewan
 */
@Configuration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
	BladeProperties.class
})
public class BladeLaunchConfiguration {

}
