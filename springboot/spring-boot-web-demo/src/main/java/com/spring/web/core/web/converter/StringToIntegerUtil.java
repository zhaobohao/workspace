

package com.spring.web.core.web.converter;

import cn.hutool.core.util.StrUtil;

/**
 * <code>
 * <pre>
 * 空字符串("")转换成Integer的null
 *
 * </pre>
 * </code>
 * @author zhaobohao
 * @date 2018-11-08
 */
public class StringToIntegerUtil {

	public static Integer convert(String source) {
		if (StrUtil.isBlank(source)){
			return null;
		}
		Integer i = Integer.parseInt(source);
		return i;
	}
}
