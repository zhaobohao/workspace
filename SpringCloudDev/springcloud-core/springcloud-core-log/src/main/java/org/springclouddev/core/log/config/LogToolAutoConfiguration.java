

package org.springclouddev.core.log.config;

import lombok.AllArgsConstructor;
import org.springclouddev.core.log.aspect.ApiLogAspect;
import org.springclouddev.core.log.event.ApiLogListener;
import org.springclouddev.core.log.event.UsualLogListener;
import org.springclouddev.core.log.event.ErrorLogListener;
import org.springclouddev.core.log.logger.SystemLogger;
import org.springclouddev.core.launch.props.SystemProperties;
import org.springclouddev.core.launch.server.ServerInfo;
import org.springclouddev.core.log.feign.ILogClient;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志工具自动配置
 *
 * @author zhaobohao
 */
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogToolAutoConfiguration {

	private final ILogClient logService;
	private final ServerInfo serverInfo;
	private final SystemProperties systemProperties;

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public SystemLogger bladeLogger() {
		return new SystemLogger();
	}

	@Bean
	public ApiLogListener apiLogListener() {
		return new ApiLogListener(logService, serverInfo, systemProperties);
	}

	@Bean
	public ErrorLogListener errorEventListener() {
		return new ErrorLogListener(logService, serverInfo, systemProperties);
	}

	@Bean
	public UsualLogListener bladeEventListener() {
		return new UsualLogListener(logService, serverInfo, systemProperties);
	}

}
