package org.springclouddev.core.cloud.hystrix;


import org.springclouddev.core.cloud.props.HystrixHeadersProperties;
import org.springframework.http.HttpHeaders;
import org.springframework.lang.Nullable;

import java.util.concurrent.Callable;

/**
 * HttpHeaders hystrix Callable
 *
 * @param <V> 泛型标记
 * @author zhaobo
 */
public class HttpHeadersCallable<V> implements Callable<V> {
	private final Callable<V> delegate;
	@Nullable
	private HttpHeaders httpHeaders;

	public HttpHeadersCallable(Callable<V> delegate,
							   @Nullable HystrixAccountGetter accountGetter,
							   HystrixHeadersProperties properties) {
		this.delegate = delegate;
		this.httpHeaders = HttpHeadersContextHolder.toHeaders(accountGetter, properties);
	}

	@Override
	public V call() throws Exception {
		if (httpHeaders == null) {
			return delegate.call();
		}
		try {
			HttpHeadersContextHolder.set(httpHeaders);
			return delegate.call();
		} finally {
			HttpHeadersContextHolder.remove();
			httpHeaders.clear();
			httpHeaders = null;
		}
	}
}
