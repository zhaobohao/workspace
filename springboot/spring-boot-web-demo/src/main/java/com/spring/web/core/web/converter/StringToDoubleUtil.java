

package com.spring.web.core.web.converter;

import org.apache.commons.lang.StringUtils;

/**
 * <code>
 * <pre>
 * 空字符串("")转换成Double的null
 *
 * </pre>
 * </code>
 * @author geekidea
 * @date 2018-11-08
 */
public class StringToDoubleUtil {

	public static Double convert(String source) {
		if (StringUtils.isBlank(source)){
			return null;
		}
		Double d = Double.parseDouble(source);
		return d;
	}
}
