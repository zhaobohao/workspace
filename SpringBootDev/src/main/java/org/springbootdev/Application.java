
package org.springbootdev;

import org.springbootdev.common.constant.CommonConstant;
import org.springbootdev.core.launch.SpingCloudDevApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

/**
 * 启动器
 *
 * @author merryChen
 */
@EnableScheduling
@SpringBootApplication
public class Application {

	public static void main(String[] args) {
		SpingCloudDevApplication.run(CommonConstant.APPLICATION_NAME, Application.class, args);
	}

}

