package org.springclouddev.core.cloud.feign;

import com.netflix.hystrix.HystrixCommand;
import feign.Contract;
import feign.Feign;
import feign.RequestInterceptor;
import feign.hystrix.HystrixFeign;
import org.springclouddev.core.tool.convert.EnumToStringConverter;
import org.springclouddev.core.tool.convert.StringToEnumConverter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.config.ConfigurableBeanFactory;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.cloud.openfeign.SpringCloudFeignClientsRegistrar;
import org.springframework.cloud.openfeign.SpringCloudHystrixTargeter;
import org.springframework.cloud.openfeign.EnableFeignClients;
import org.springframework.cloud.openfeign.Targeter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Scope;
import org.springframework.core.convert.ConversionService;
import org.springframework.core.convert.converter.ConverterRegistry;

import java.util.ArrayList;


/**
 *  feign 增强配置
 *
 * @author zhaobo
 */
@Configuration
@ConditionalOnClass(Feign.class)
@Import(SpringCloudFeignClientsRegistrar.class)
@AutoConfigureAfter(EnableFeignClients.class)
public class FeignAutoConfiguration {

	@Bean
	@ConditionalOnMissingBean
	public Targeter springCloudFeignTargeter() {
		return new SpringCloudHystrixTargeter();
	}

	@Configuration("hystrixFeignConfiguration")
	@ConditionalOnClass({ HystrixCommand.class, HystrixFeign.class })
	protected static class HystrixFeignConfiguration {
		@Bean
		@Scope(ConfigurableBeanFactory.SCOPE_PROTOTYPE)
		@ConditionalOnProperty("feign.hystrix.enabled")
		public Feign.Builder feignHystrixBuilder(
			RequestInterceptor requestInterceptor, Contract feignContract) {
			return HystrixFeign.builder()
				.contract(feignContract)
				.decode404()
				.requestInterceptor(requestInterceptor);
		}

		@Bean
		@ConditionalOnMissingBean
		public RequestInterceptor requestInterceptor() {
			return new FeignRequestHeaderInterceptor();
		}
	}

	/**
	 *  enum 《-》 String 转换配置
	 * @param conversionService ConversionService
	 * @return SpringMvcContract
	 */
	@Bean
	public Contract feignContract(@Qualifier("mvcConversionService") ConversionService conversionService) {
		ConverterRegistry converterRegistry =  ((ConverterRegistry) conversionService);
		converterRegistry.addConverter(new EnumToStringConverter());
		converterRegistry.addConverter(new StringToEnumConverter());
		return new FeignSpringMvcContract(new ArrayList<>(), conversionService);
	}
}
