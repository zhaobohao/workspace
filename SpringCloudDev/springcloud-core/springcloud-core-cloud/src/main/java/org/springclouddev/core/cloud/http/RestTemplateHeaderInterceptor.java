package org.springclouddev.core.cloud.http;

import lombok.AllArgsConstructor;
import org.springclouddev.core.cloud.hystrix.HttpHeadersContextHolder;
import org.springclouddev.core.cloud.hystrix.HystrixAccountGetter;
import org.springclouddev.core.cloud.props.HystrixHeadersProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpRequest;
import org.springframework.http.client.ClientHttpRequestExecution;
import org.springframework.http.client.ClientHttpRequestInterceptor;
import org.springframework.http.client.ClientHttpResponse;
import org.springframework.lang.Nullable;

import java.io.IOException;

/**
 * RestTemplateHeaderInterceptor 传递Request header
 *
 * @author zhaobo
 */
@AllArgsConstructor
public class RestTemplateHeaderInterceptor implements ClientHttpRequestInterceptor {
	@Nullable
	private final HystrixAccountGetter accountGetter;
	private final HystrixHeadersProperties properties;

	@Override
	public ClientHttpResponse intercept(
		HttpRequest request, byte[] bytes,
		ClientHttpRequestExecution execution) throws IOException {
		HttpHeaders headers = HttpHeadersContextHolder.get();
		// 考虑2中情况 1. RestTemplate 不是用 hystrix 2. 使用 hystrix
		if (headers == null) {
			headers = HttpHeadersContextHolder.toHeaders(accountGetter, properties);
		}
		if (headers != null && !headers.isEmpty()) {
			HttpHeaders httpHeaders = request.getHeaders();
			headers.forEach((key, values) -> {
				values.forEach(value -> httpHeaders.add(key, value));
			});
		}
		return execution.execute(request, bytes);
	}
}
