package org.springclouddev.core.cloud.version;

import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * url版本号处理
 *
  * @author zhaobo
 */
@Configuration
@ConditionalOnWebApplication
public class VersionMappingAutoConfiguration {
	@Bean
	public WebMvcRegistrations getWebMvcRegistrations() {
		return new SpringCloudWebMvcRegistrations();
	}
}
