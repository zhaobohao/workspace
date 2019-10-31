
package org.springclouddev.desk;

import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

/**
 * Desk启动器
 *
 * @author zhaobohao
 */
@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@EnableConfigurationProperties
public class DeskApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_DESK_NAME, DeskApplication.class, args);
	}

}

