
package org.springbootdev.core.launch.config;

import lombok.AllArgsConstructor;
import org.springbootdev.core.launch.props.SystemProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;

/**
 * 配置类
 *
 * @author zhaobohao
 */
@Configuration
@AllArgsConstructor
@Order(Ordered.HIGHEST_PRECEDENCE)
@EnableConfigurationProperties({
	SystemProperties.class
})
public class SpringCloudLaunchConfiguration {

}
