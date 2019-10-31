
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
@AllArgsConstructor
@EnableConfigurationProperties({
	SpringCloudAsyncProperties.class
})
public class SystemExecutorConfiguration extends AsyncConfigurerSupport {

	private final SpringCloudAsyncProperties springCloudAsyncProperties;

	@Override
	@Bean(name = "asyTaskExecutor")
	public Executor getAsyncExecutor() {
		ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
		executor.setCorePoolSize(springCloudAsyncProperties.getCorePoolSize());
		executor.setMaxPoolSize(springCloudAsyncProperties.getMaxPoolSize());
		executor.setQueueCapacity(springCloudAsyncProperties.getQueueCapacity());
		executor.setKeepAliveSeconds(springCloudAsyncProperties.getKeepAliveSeconds());
		executor.setThreadNamePrefix("async-executor-");
		// rejection-policy：当pool已经达到max size的时候，如何处理新任务
		// CALLER_RUNS：不在新线程中执行任务，而是有调用者所在的线程来执行
		executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
		executor.setWaitForTasksToCompleteOnShutdown(true);
		//执行初始化
		executor.initialize();
		return executor;
	}

	@Override
	public AsyncUncaughtExceptionHandler getAsyncUncaughtExceptionHandler() {
		return new SimpleAsyncUncaughtExceptionHandler();
	}

}
