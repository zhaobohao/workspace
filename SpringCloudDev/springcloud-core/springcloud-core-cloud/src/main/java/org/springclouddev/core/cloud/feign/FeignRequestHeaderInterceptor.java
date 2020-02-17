
package org.springclouddev.core.cloud.feign;

import feign.RequestInterceptor;
import feign.RequestTemplate;
import org.springclouddev.core.cloud.hystrix.HttpHeadersContextHolder;
import org.springframework.http.HttpHeaders;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.servlet.http.HttpServletRequest;
import java.util.Enumeration;

/**
 * feign 传递Request header
 *
 * <p>
 * https://blog.csdn.net/u014519194/article/details/77160958
 * http://tietang.wang/2016/02/25/hystrix/Hystrix%E5%8F%82%E6%95%B0%E8%AF%A6%E8%A7%A3/
 * https://github.com/Netflix/Hystrix/issues/92#issuecomment-260548068
 * </p>
 *
 * @author zhaobo
 */
public class FeignRequestHeaderInterceptor implements RequestInterceptor {

	@Override
	public void apply(RequestTemplate requestTemplate) {
		HttpHeaders headers = HttpHeadersContextHolder.get();
		if (headers != null && !headers.isEmpty()) {
			headers.forEach((key, values) -> {
				values.forEach(value -> requestTemplate.header(key, value));
			});
		}
		ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
		HttpServletRequest request = attributes.getRequest();
		Enumeration<String> headerNames = request.getHeaderNames();
		//添加token
		while (headerNames.hasMoreElements()) {
			String key = headerNames.nextElement();
			requestTemplate.header(key, request.getHeader(key));
		}
	}

}
