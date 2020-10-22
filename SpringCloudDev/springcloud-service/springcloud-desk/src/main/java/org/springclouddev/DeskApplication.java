package org.springclouddev;

import com.ctrip.framework.apollo.spring.annotation.EnableApolloConfig;
import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Desk启动器
 *
 * @author zhaobohao
 */
@SpringCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
@EnableConfigurationProperties
@EnableApolloConfig
public class DeskApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_DESK_NAME, DeskApplication.class, args);
	}

}

