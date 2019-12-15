
package org.springbootdev;

import org.springbootdev.common.constant.CommonConstant;
import org.springbootdev.core.launch.SpingBootDevApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

/**
 * 启动器
 *
 * @author merryChen
 */
@SpringBootApplication
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
		SpingBootDevApplication.run(CommonConstant.APPLICATION_NAME, Application.class, args);
	}

}

