
package org.springblade.resource;

import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 资源启动器
 *
 * @author firewan
 */
@SpringCloudApplication
public class ResourceApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_RESOURCE_NAME, ResourceApplication.class, args);
	}

}

