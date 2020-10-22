package org.springclouddev.gateway;

import org.springclouddev.core.launch.constant.AppConstant;
import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.hystrix.EnableHystrix;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 项目启动
 *
 * @author zhaobohao
 */
@EnableHystrix
@EnableScheduling
@SpringCloudApplication
public class GateWayApplication {

	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_GATEWAY_NAME, GateWayApplication.class, args);
	}

}
