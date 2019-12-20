
package org.springclouddev.common.launch;

import org.springclouddev.common.constant.LauncherConstant;
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
		props.setProperty("spring.cloud.nacos.discovery.server-addr", LauncherConstant.nacosAddr(profile));
		props.setProperty("spring.cloud.nacos.config.server-addr", LauncherConstant.nacosAddr(profile));
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_flow.nacos.server-addr",LauncherConstant.nacosAddr(profile) );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_degrade.nacos.server-addr",LauncherConstant.nacosAddr(profile) );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_param-flow.nacos.server-addr",LauncherConstant.nacosAddr(profile) );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_system.nacos.server-addr",LauncherConstant.nacosAddr(profile) );
		props.setProperty("spring.cloud.sentinel.datasource.springcloud_authority.nacos.server-addr", LauncherConstant.nacosAddr(profile) );


		props.setProperty("spring.cloud.sentinel.transport.dashboard", LauncherConstant.sentinelAddr(profile));
	}

}
