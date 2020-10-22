package org.springclouddev.admin;

import de.codecentric.boot.admin.server.config.EnableAdminServer;
import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * admin启动器
 *
 * @author zhaobohao
 */
@EnableAdminServer
@SpringCloudApplication
public class AdminApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_ADMIN_NAME, AdminApplication.class, args);
	}

}
