
package org.springbootdev.core.tool.support.xss;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Xss配置类
 *
 * @author zhaobohao
 */
@Data
@ConfigurationProperties("springbootdev.xss")
public class XssProperties {

	/**
	 * 开启xss
	 */
	private Boolean enable = true;

	/**
	 * 放行url
	 */
	private List<String> skipUrl = new ArrayList<>();

}
