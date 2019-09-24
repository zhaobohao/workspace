
package org.springclouddev.seata.order;

import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springclouddev.core.transaction.annotation.SeataCloudApplication;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Order启动器
 *
 * @author zhaobo
 */
@SeataCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class SeataOrderApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run("springcloud-seata-order", SeataOrderApplication.class, args);
	}

}

