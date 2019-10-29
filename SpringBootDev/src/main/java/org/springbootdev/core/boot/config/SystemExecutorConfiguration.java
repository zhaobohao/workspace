
package org.springbootdev.core.boot.config;

import lombok.AllArgsConstructor;
import org.springbootdev.core.boot.props.SpringCloudAsyncProperties;
import org.springframework.aop.interceptor.AsyncUncaughtExceptionHandler;
import org.springframework.aop.interceptor.SimpleAsyncUncaughtExceptionHandler;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.AsyncConfigurerSupport;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * 异步处理
 *
 * @author zhaobohao
 */
@Configuration
@EnableAsync
@EnableScheduling
@AllArgsConstructor
@EnableConfigurationProperties({
	SpringCloudAsyncProperties.class
})
public class SystemExecutorConfiguration extends AsyncConfigurerSupport {

	private final SpringCloudAsyncProperties springCloudAsyncProperties;

	@Override
	@Bean(name = "taskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(springCloudAsyncProperties.getCorePoolSize());
		executor.setMaxPoolSize(springCloudAsyncProperties.getMaxPoolSize());
		executor.setQueueCapacity(springCloudAsyncProperties.getQueueCapacity());
		executor.setKeepAliveSeconds(springCloudAsyncProperties.getKeepAliveSeconds());
		executor.setThreadNamePrefix("async-executor-");
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

}
