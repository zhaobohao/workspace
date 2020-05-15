package org.springclouddev;

import org.springclouddev.core.cloud.feign.EnableCustomFeign;
import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 积分模块启动器
 * @author zhaobohao
 */
@SpringCloudApplication
@EnableCustomFeign(AppConstant.BASE_PACKAGES)
@EnableConfigurationProperties
public class DroolsApplication {
	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_DROOLS_NAME, DroolsApplication.class, args);
	}
}

