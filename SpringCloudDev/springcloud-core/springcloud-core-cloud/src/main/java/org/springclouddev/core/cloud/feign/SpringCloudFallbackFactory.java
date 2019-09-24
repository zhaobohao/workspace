package org.springclouddev.core.cloud.feign;

import feign.Target;
import lombok.AllArgsConstructor;
import org.springframework.cglib.proxy.Enhancer;

/**
 * 默认 Fallback，避免写过多fallback类
 *
 * @param <T> 泛型标记
 * @author zhaobo
 */
@AllArgsConstructor
public class SpringCloudFallbackFactory<T> implements feign.hystrix.FallbackFactory<T> {
	private final Target<T> target;

	@Override
	@SuppressWarnings("unchecked")
	public T create(Throwable cause) {
		final Class<T> targetType = target.type();
		final String targetName = target.name();
		Enhancer enhancer = new Enhancer();
		enhancer.setSuperclass(targetType);
		enhancer.setUseCache(true);
		enhancer.setCallback(new FeignFallback<>(targetType, targetName, cause));
		return (T) enhancer.create();
	}
}
