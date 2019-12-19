
package org.springbootdev.common.config;


import org.springbootdev.core.launch.constant.AppConstant;
import org.springbootdev.core.secure.registry.SecureRegistry;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author merryChen
 */
@Configuration
public class BaseMvcConfiguration implements WebMvcConfigurer {

	@Bean
	public SecureRegistry secureRegistry() {
		SecureRegistry secureRegistry = new SecureRegistry();
		secureRegistry.setEnable(true);
		secureRegistry.excludePathPatterns("/"+AppConstant.APPLICATION_AUTH_NAME+"/token");
		secureRegistry.excludePathPatterns("/"+AppConstant.APPLICATION_LOG_NAME +"/log/**");
		secureRegistry.excludePathPatterns("/"+AppConstant.APPLICATION_SYSTEM_NAME +"/menu/auth-routes");
		secureRegistry.excludePathPatterns("/"+AppConstant.APPLICATION_SYSTEM_NAME +"/menu/routes");
		secureRegistry.excludePathPatterns("/doc.html");
		secureRegistry.excludePathPatterns("/js/**");
		secureRegistry.excludePathPatterns("/");
		secureRegistry.excludePathPatterns("/index.html");
		secureRegistry.excludePathPatterns("/webjars/**");
		secureRegistry.excludePathPatterns("/swagger-resources/**");
		secureRegistry.excludePathPatterns("/example");
		secureRegistry.excludePathPatterns("/actuator/health/**");
		secureRegistry.excludePathPatterns("/v2/api-docs/**");
		secureRegistry.excludePathPatterns("/v2/api-docs-ext/**");
		secureRegistry.excludePathPatterns("**/auth/**");
		secureRegistry.excludePathPatterns("**/error/**");
		secureRegistry.excludePathPatterns("/assets/**");
		secureRegistry.excludePathPatterns("/static/**");
		secureRegistry.excludePathPatterns("/img/**");
		return secureRegistry;
	}

	@Override
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
		registry.addResourceHandler("/js/**").addResourceLocations("classpath:/js/")
		;
		registry.addResourceHandler("doc.html")
			.addResourceLocations("classpath:/META-INF/resources/");

		registry.addResourceHandler("index.html")
			.addResourceLocations("classpath:/static/");

		registry.addResourceHandler("/static/**")
			.addResourceLocations("classpath:/static/static/");
		registry.addResourceHandler("/img/**")
			.addResourceLocations("classpath:/static/img/");

		registry.addResourceHandler("/webjars/**")
			.addResourceLocations("classpath:/META-INF/resources/webjars/");
	}

}
