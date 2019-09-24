package org.springclouddev.core.cloud.version;

import org.springclouddev.core.cloud.annotation.ApiVersion;
import org.springclouddev.core.cloud.annotation.UrlVersion;
import org.springclouddev.core.tool.utils.StringPool;
import org.springclouddev.core.tool.utils.StringUtil;
import org.springframework.core.annotation.AnnotatedElementUtils;
import org.springframework.lang.Nullable;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.util.Map;

/**
 * url版本号处理 和 header 版本处理
 *
 * <p>
 *     url: /v1/user/{id}
 *     header: Accept application/vnd.springcloud.VERSION+json
 * </p>
 *
 * 注意：c 代表客户端版本
 *
 * @author zhaobo
 */
public class SpringCloudRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

	@Nullable
	@Override
	protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
		RequestMappingInfo mappinginfo = super.getMappingForMethod(method, handlerType);
		if (mappinginfo != null) {
			RequestMappingInfo apiVersionMappingInfo = getApiVersionMappingInfo(method, handlerType);
			return apiVersionMappingInfo == null ? mappinginfo : apiVersionMappingInfo.combine(mappinginfo);
		}
		return null;
	}

	@Nullable
	private RequestMappingInfo getApiVersionMappingInfo(Method method, Class<?> handlerType) {
		// url 上的版本，优先获取方法上的版本
		UrlVersion urlVersion = AnnotatedElementUtils.findMergedAnnotation(method, UrlVersion.class);
		// 再次尝试类上的版本
		if (urlVersion == null || StringUtil.isBlank(urlVersion.value())) {
			urlVersion = AnnotatedElementUtils.findMergedAnnotation(handlerType, UrlVersion.class);
		}
		// Media Types 版本信息
		ApiVersion apiVersion = AnnotatedElementUtils.findMergedAnnotation(method, ApiVersion.class);
		// 再次尝试类上的版本
		if (apiVersion == null || StringUtil.isBlank(apiVersion.value())) {
			apiVersion = AnnotatedElementUtils.findMergedAnnotation(handlerType, ApiVersion.class);
		}
		boolean nonUrlVersion = urlVersion == null || StringUtil.isBlank(urlVersion.value());
		boolean nonApiVersion = apiVersion == null || StringUtil.isBlank(apiVersion.value());
		// 先判断同时不纯在
		if (nonUrlVersion && nonApiVersion) {
			return null;
		}
		// 如果 header 版本不存在
		RequestMappingInfo.Builder mappingInfoBuilder = null;
		if (nonApiVersion) {
			mappingInfoBuilder = RequestMappingInfo.paths(urlVersion.value());
		} else {
			mappingInfoBuilder = RequestMappingInfo.paths(StringPool.EMPTY);
		}
		// 如果url版本不存在
		if (nonUrlVersion) {
			String vsersionMediaTypes = new SpringCloudMediaType(apiVersion.value()).toString();
			mappingInfoBuilder.produces(vsersionMediaTypes);
		}
		return mappingInfoBuilder.build();
	}

	@Override
	protected void handlerMethodsInitialized(Map<RequestMappingInfo, HandlerMethod> handlerMethods) {
		// 打印路由信息 spring boot 2.1 去掉了这个 日志的打印
		if (logger.isInfoEnabled()) {
			for (Map.Entry<RequestMappingInfo, HandlerMethod> entry : handlerMethods.entrySet()) {
				RequestMappingInfo mapping = entry.getKey();
				HandlerMethod handlerMethod = entry.getValue();
				logger.info("Mapped \"" + mapping + "\" onto " + handlerMethod);
			}
		}
		super.handlerMethodsInitialized(handlerMethods);
	}
}
