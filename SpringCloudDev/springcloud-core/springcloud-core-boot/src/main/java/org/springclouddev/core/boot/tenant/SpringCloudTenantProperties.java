
package org.springclouddev.core.boot.tenant;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 多租户配置
 *
 * @author zhaobohao
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "springcloud.tenant")
public class SpringCloudTenantProperties {

	/**
	 * 多租户字段名称
	 */
	private String column = "tenant_id";

	/**
	 * 多租户数据表
	 */
	private List<String> tables = new ArrayList<>();

	/**
	 * 多租户系统数据表
	 */
	private List<String> mkTable = Arrays.asList("mk_notice", "mk_log_api", "mk_log_error", "mk_log_usual");
}
