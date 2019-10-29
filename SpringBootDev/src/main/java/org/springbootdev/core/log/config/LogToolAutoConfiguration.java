

package org.springbootdev.core.log.config;

import lombok.AllArgsConstructor;
import org.springbootdev.core.launch.props.SystemProperties;
import org.springbootdev.core.launch.server.ServerInfo;
import org.springbootdev.core.log.aspect.ApiLogAspect;
import org.springbootdev.core.log.event.ApiLogListener;
import org.springbootdev.core.log.event.ErrorLogListener;
import org.springbootdev.core.log.event.UsualLogListener;
import org.springbootdev.core.log.logger.SystemLogger;
import org.springbootdev.modules.system.service.ILogService;
import org.springframework.boot.autoconfigure.condition.ConditionalOnWebApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * 日志工具自动配置
 *
 * @author merryChen
 */
@Configuration
@AllArgsConstructor
@ConditionalOnWebApplication
public class LogToolAutoConfiguration {

	private final ILogService logService;
	private final ServerInfo serverInfo;
	private final SystemProperties systemProperties;

	@Bean
	public ApiLogAspect apiLogAspect() {
		return new ApiLogAspect();
	}

	@Bean
	public SystemLogger systemLogger() {
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
	public UsualLogListener usualEventListener() {
		return new UsualLogListener(logService, serverInfo, systemProperties);
	}

}
