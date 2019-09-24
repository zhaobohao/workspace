package org.springclouddev.core.cloud.hystrix;

import com.netflix.hystrix.Hystrix;
import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import org.springclouddev.core.cloud.props.HystrixHeadersProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.lang.Nullable;

import javax.annotation.PostConstruct;

/**
 * Hystrix 配置
 *
 * @author zhaobo
 */
@Configuration
@ConditionalOnClass(Hystrix.class)
@EnableConfigurationProperties(HystrixHeadersProperties.class)
public class HystrixAutoConfiguration {
	@Nullable
	@Autowired(required = false)
	private com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy existingConcurrencyStrategy;
	@Nullable
	@Autowired(required = false)
	private HystrixAccountGetter accountGetter;
	@Autowired
	private HystrixHeadersProperties properties;

	@PostConstruct
	public void init() {
		// Keeps references of existing Hystrix plugins.
		HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
		HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
		HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();
		HystrixCommandExecutionHook commandExecutionHook = HystrixPlugins.getInstance().getCommandExecutionHook();

		HystrixPlugins.reset();

		// Registers existing plugins excepts the Concurrent Strategy plugin.
		com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy strategy = new ExtraHystrixConcurrencyStrategy(existingConcurrencyStrategy, accountGetter, properties);
		HystrixPlugins.getInstance().registerConcurrencyStrategy(strategy);
		HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
		HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
		HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
		HystrixPlugins.getInstance().registerCommandExecutionHook(commandExecutionHook);
	}

}
