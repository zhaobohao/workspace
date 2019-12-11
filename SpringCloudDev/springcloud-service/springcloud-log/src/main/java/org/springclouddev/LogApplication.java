
package org.springclouddev;

import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 日志服务
 *
 * @author zhaobohao
 */
@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@EnableConfigurationProperties
public class LogApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_LOG_NAME, LogApplication.class, args);
	}

}
