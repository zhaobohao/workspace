package org.springclouddev.core.cloud.version;

import org.springframework.boot.autoconfigure.web.servlet.WebMvcRegistrations;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

/**
 * url版本号处理
 *
 * @author zhaobo
 */
public class SpringCloudWebMvcRegistrations implements WebMvcRegistrations {
	@Override
	public RequestMappingHandlerMapping getRequestMappingHandlerMapping() {
		return new SpringCloudRequestMappingHandlerMapping();
	}

	@Override
	public RequestMappingHandlerAdapter getRequestMappingHandlerAdapter() {
		return null;
	}

	@Override
	public ExceptionHandlerExceptionResolver getExceptionHandlerExceptionResolver() {
		return null;
	}
}
