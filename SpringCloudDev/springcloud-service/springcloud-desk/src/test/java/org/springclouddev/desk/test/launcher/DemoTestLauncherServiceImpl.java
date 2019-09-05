
package org.springclouddev.desk.test.launcher;

import org.springclouddev.common.constant.CommonConstant;
import org.springclouddev.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author zhaobohao
 */
public class DemoTestLauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
		Properties props = System.getProperties();
		props.setProperty("spring.cloud.nacos.discovery.server-addr", CommonConstant.NACOS_DEV_ADDR);
		props.setProperty("spring.cloud.nacos.config.server-addr", CommonConstant.NACOS_DEV_ADDR);
		props.setProperty("spring.cloud.sentinel.transport.dashboard", CommonConstant.SENTINEL_DEV_ADDR);
	}

	@Override
	public int getOrder() {
		return 10;
	}
}
