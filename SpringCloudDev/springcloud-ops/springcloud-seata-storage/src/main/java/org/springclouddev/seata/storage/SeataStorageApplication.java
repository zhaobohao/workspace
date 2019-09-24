
package org.springclouddev.seata.storage;

import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springclouddev.core.transaction.annotation.SeataCloudApplication;
import org.springframework.cloud.client.circuitbreaker.EnableCircuitBreaker;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * Storage启动器
 *
 * @author zhaobo
 */
@SeataCloudApplication
@EnableFeignClients(AppConstant.BASE_PACKAGES)
public class SeataStorageApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run("springcloud-seata-storage", SeataStorageApplication.class, args);
	}

}

