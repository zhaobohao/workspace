

package com.spring.web.core.web.converter;

import org.springframework.core.convert.converter.Converter;

/**
 * <code>
 *
 * </code>
 * @author geekidea
 * @date 2018-11-08
 */
public class StringToIntegerConverter implements Converter<String, Integer> {

	@Override
	public Integer convert(String source) {
		return StringToIntegerUtil.convert(source);
	}
}
