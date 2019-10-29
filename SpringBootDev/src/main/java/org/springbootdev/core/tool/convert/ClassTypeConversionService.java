package org.springbootdev.core.tool.convert;

import org.springframework.boot.convert.ApplicationConversionService;
import org.springframework.core.convert.support.GenericConversionService;
import org.springframework.lang.Nullable;
import org.springframework.util.StringValueResolver;

/**
 * 类型 转换 服务，添加了 IEnum 转换
 *
 * @author zhaobo
 */
public class ClassTypeConversionService extends ApplicationConversionService {
	@Nullable
	private static volatile ClassTypeConversionService SHARED_INSTANCE;

	public ClassTypeConversionService() {
		this(null);
	}

	public ClassTypeConversionService(@Nullable StringValueResolver embeddedValueResolver) {
		super(embeddedValueResolver);
		super.addConverter(new EnumToStringConverter());
		super.addConverter(new StringToEnumConverter());
	}

	/**
	 * Return a shared default application {@code ConversionService} instance, lazily
	 * building it once needed.
	 * <p>
	 * Note: This method actually returns an {@link ClassTypeConversionService}
	 * instance. However, the {@code ConversionService} signature has been preserved for
	 * binary compatibility.
	 * @return the shared {@code SpringCloudConversionService} instance (never{@code null})
	 */
	public static GenericConversionService getInstance() {
		ClassTypeConversionService sharedInstance = ClassTypeConversionService.SHARED_INSTANCE;
		if (sharedInstance == null) {
			synchronized (ClassTypeConversionService.class) {
				sharedInstance = ClassTypeConversionService.SHARED_INSTANCE;
				if (sharedInstance == null) {
					sharedInstance = new ClassTypeConversionService();
					ClassTypeConversionService.SHARED_INSTANCE = sharedInstance;
				}
			}
		}
		return sharedInstance;
	}

}
