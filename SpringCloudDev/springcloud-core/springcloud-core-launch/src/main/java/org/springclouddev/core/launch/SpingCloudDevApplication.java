package org.springclouddev.core.launch;

import org.springclouddev.core.launch.constant.AppConstant;
import org.springclouddev.core.launch.constant.NacosConstant;
import org.springclouddev.core.launch.constant.SentinelConstant;
import org.springclouddev.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.env.*;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

/**
 * 项目启动器，搞定环境变量问题
 *
 * @author zhaobohao
 */
public class SpingCloudDevApplication {

	/**
	 * Create an application context
	 * java -jar app.jar --spring.profiles.active=prod --server.port=2333
	 *
	 * @param appName application name
	 * @param source  The sources
	 * @return an application context created from the current state
	 */
	public static ConfigurableApplicationContext run(String appName, Class source, String... args) {
		SpringApplicationBuilder builder = createSpringApplicationBuilder(appName, source, args);
		return builder.run(args);
	}

	public static SpringApplicationBuilder createSpringApplicationBuilder(String appName, Class source, String... args) {
		Assert.hasText(appName, "[appName]服务名不能为空");
		// 读取环境变量，使用spring boot的规则
		ConfigurableEnvironment environment = new StandardEnvironment();
		MutablePropertySources propertySources = environment.getPropertySources();
		propertySources.addFirst(new SimpleCommandLinePropertySource(args));
		propertySources.addLast(new MapPropertySource(StandardEnvironment.SYSTEM_PROPERTIES_PROPERTY_SOURCE_NAME, environment.getSystemProperties()));
		propertySources.addLast(new SystemEnvironmentPropertySource(StandardEnvironment.SYSTEM_ENVIRONMENT_PROPERTY_SOURCE_NAME, environment.getSystemEnvironment()));
		// 获取配置的环境变量
		String[] activeProfiles = environment.getActiveProfiles();
		// 判断环境:dev、test、prod
		List<String> profiles = Arrays.asList(activeProfiles);
		// 预设的环境
		List<String> presetProfiles = new ArrayList<>(Arrays.asList(AppConstant.DEV_CODE, AppConstant.FAT_CODE, AppConstant.UAT_CODE, AppConstant.SIT_CODE, AppConstant.TEST_CODE, AppConstant.PROD_CODE));
		// 交集
		presetProfiles.retainAll(profiles);
		// 当前使用
		List<String> activeProfileList = new ArrayList<>(profiles);
		Function<Object[], String> joinFun = StringUtils::arrayToCommaDelimitedString;
		SpringApplicationBuilder builder = new SpringApplicationBuilder(source);
		String profile;
		if (activeProfileList.isEmpty()) {
			// 默认dev开发
			profile = AppConstant.DEV_CODE;
			activeProfileList.add(profile);
			builder.profiles(profile);
		} else if (activeProfileList.size() == 1) {
			profile = activeProfileList.get(0);
		} else {
			// 同时存在dev、test、prod环境时
			throw new RuntimeException("同时存在环境变量:[" + StringUtils.arrayToCommaDelimitedString(activeProfiles) + "]");
		}
		String startJarPath = SpingCloudDevApplication.class.getResource("/").getPath().split("!")[0];
		String activePros = joinFun.apply(activeProfileList.toArray());
		System.out.println(String.format("----启动中，读取到的环境变量:[%s]，jar地址:[%s]----", activePros, startJarPath));
		Properties props = System.getProperties();
		props.setProperty("spring.main.allow-bean-definition-overriding","true");
		props.setProperty("spring.application.name", appName);
		props.setProperty("spring.profiles.active", profile);
		props.setProperty("info.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("info.desc", appName);
		props.setProperty("springclouddev.env", profile);
		props.setProperty("springclouddev.name", appName);
		props.setProperty("springclouddev.is-local", String.valueOf(isLocalDev()));
		props.setProperty("springclouddev.dev-mode", profile.equals(AppConstant.PROD_CODE) ? "false" : "true");
		props.setProperty("springclouddev.service.version", AppConstant.APPLICATION_VERSION);
		props.setProperty("spring.main.allow-bean-definition-overriding", "true");
		props.setProperty("spring.cloud.nacos.discovery.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.nacos.config.server-addr", NacosConstant.NACOS_ADDR);
		props.setProperty("spring.cloud.nacos.config.prefix", NacosConstant.NACOS_CONFIG_PREFIX);
		props.setProperty("spring.cloud.nacos.config.file-extension", NacosConstant.NACOS_CONFIG_FORMAT);
		props.setProperty("spring.cloud.sentinel.transport.dashboard", SentinelConstant.SENTINEL_ADDR);

		props.setProperty("spring.cloud.sentinel.datasource.springcloud_flow.nacos.server-addr",NacosConstant.NACOS_ADDR );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_flow.nacos.dataId", appName+"-flow-rules");
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_flow.nacos.groupId","SENTINEL_GROUP" );
		//# 规则类型，取值见：com.alibaba.cloud.sentinel.datasource.RuleType
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_flow.nacos.rule-type", "flow");

		props.setProperty("spring.cloud.sentinel.datasource.springcloud_degrade.nacos.server-addr",NacosConstant.NACOS_ADDR );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_degrade.nacos.dataId", appName+"-degrade-rules");
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_degrade.nacos.groupId","SENTINEL_GROUP" );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_degrade.nacos.rule-type", "degrade");

		props.setProperty("spring.cloud.sentinel.datasource.springcloud_param-flow.nacos.server-addr",NacosConstant.NACOS_ADDR );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_param-flow.nacos.dataId", appName+"-param-flow-rules");
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_param-flow.nacos.groupId","SENTINEL_GROUP" );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_param-flow.nacos.rule-type", "param-flow");

		props.setProperty("spring.cloud.sentinel.datasource.springcloud_system.nacos.server-addr",NacosConstant.NACOS_ADDR );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_system.nacos.dataId", appName+"-system-rules");
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_system.nacos.groupId","SENTINEL_GROUP" );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_system.nacos.rule-type", "system");

		props.setProperty("spring.cloud.sentinel.datasource.springcloud_authority.nacos.server-addr",NacosConstant.NACOS_ADDR );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_authority.nacos.dataId", appName+"-authority-rules");
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_authority.nacos.groupId","SENTINEL_GROUP" );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_authority.nacos.rule-type", "authority");
		props.setProperty("spring.cloud.alibaba.seata.tx-service-group", appName.concat(NacosConstant.NACOS_GROUP_SUFFIX));
		// 加载自定义组件
		List<LauncherService> launcherList = new ArrayList<>();
		ServiceLoader.load(LauncherService.class).forEach(launcherList::add);
		launcherList.stream().sorted(Comparator.comparing(LauncherService::getOrder)).collect(Collectors.toList())
			.forEach(launcherService -> launcherService.launcher(builder, appName, profile));
		return builder;
	}

	/**
	 * 判断是否为本地开发环境
	 *
	 * @return boolean
	 */
	public static boolean isLocalDev() {
		String osName = System.getProperty("os.name");
		return StringUtils.hasText(osName) && !(AppConstant.OS_NAME_LINUX.equals(osName.toUpperCase()));
	}

}
