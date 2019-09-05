
package org.springclouddev.common.launch;

import org.springclouddev.common.constant.CommonConstant;
import org.springclouddev.core.launch.service.LauncherService;
import org.springframework.boot.builder.SpringApplicationBuilder;

import java.util.Properties;

/**
 * 启动参数拓展
 *
 * @author zhaobohao
 */
public class LauncherServiceImpl implements LauncherService {

	@Override
	public void launcher(SpringApplicationBuilder builder, String appName, String profile) {
		Properties props = System.getProperties();
		props.setProperty("spring.cloud.nacos.discovery.server-addr", CommonConstant.nacosAddr(profile));
		props.setProperty("spring.cloud.nacos.config.server-addr", CommonConstant.nacosAddr(profile));
		props.setProperty("spring.cloud.sentinel.transport.dashboard", CommonConstant.sentinelAddr(profile));
	}

}
