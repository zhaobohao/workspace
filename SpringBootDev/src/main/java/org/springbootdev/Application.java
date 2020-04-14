
package org.springbootdev;

import org.springbootdev.common.constant.CommonConstant;
import org.springbootdev.core.launch.SpingBootDevApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;

/**
 * 启动器
 *
 * @author merryChen
 */
@SpringBootApplication
@ServletComponentScan   //启动器启动时，扫描本目录以及子目录带有的webservlet注解的
@EnableConfigurationProperties
public class Application {

	public static void main(String[] args) {
		SpingBootDevApplication.run(CommonConstant.APPLICATION_NAME, Application.class, args);
	}

}

