
package org.springclouddev.seata.storage.config;


import org.springclouddev.core.secure.registry.SecureRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * secure模块api放行配置
 *
 * @author zhaobo
 */
@Configuration
public class StorageConfiguration implements WebMvcConfigurer {

	@Bean
	public SecureRegistry secureRegistry() {
		SecureRegistry secureRegistry = new SecureRegistry();
		secureRegistry.excludePathPatterns("/deduct/**");
		return secureRegistry;
	}

}
