
package org.springclouddev;

import org.springclouddev.core.cloud.feign.EnableCustomFeign;
import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 系统模块启动器
 * @author zhaobohao
 */
@SpringCloudApplication
@EnableCustomFeign(AppConstant.BASE_PACKAGES)
@EnableConfigurationProperties
public class SystemApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_SYSTEM_NAME, SystemApplication.class, args);
	}

}

