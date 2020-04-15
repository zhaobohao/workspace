package org.springclouddev;

import net.hasor.spring.boot.EnableHasor;
import net.hasor.spring.boot.EnableHasorWeb;
import org.springclouddev.core.cloud.feign.EnableCustomFeign;
import org.springclouddev.core.launch.SpingCloudDevApplication;
import org.springclouddev.core.launch.constant.AppConstant;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.client.SpringCloudApplication;

/**
 * 积分模块启动器
 * @author zhaobohao
 */
@SpringCloudApplication
@EnableCustomFeign(AppConstant.BASE_PACKAGES)
@EnableConfigurationProperties
@EnableHasor()      // 在Spring 中启用 Hasor
@EnableHasorWeb()   // 将 hasor-web 配置到 Spring 环境中，Dataway 的 UI 是通过 hasor-web 提供服务。
public class HasorApplication {
	public static void main(String[] args) {
		SpingCloudDevApplication.run(AppConstant.APPLICATION_HASOR_NAME, HasorApplication.class, args);
	}
}

