
package org.springclouddev;

import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 用户启动器
 *
 * @author zhaobohao
 */
@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@EnableConfigurationProperties
public class UserApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_USER_NAME, UserApplication.class, args);
	}

}
