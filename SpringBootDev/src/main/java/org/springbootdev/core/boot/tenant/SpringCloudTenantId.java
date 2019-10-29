
package org.springbootdev.core.boot.tenant;

import org.springbootdev.core.tool.utils.RandomType;
import org.springbootdev.core.tool.utils.StringUtil;

/**
 * 租户id生成器
 *
 * @author zhaobohao
 */
public class SpringCloudTenantId implements TenantId {
	@Override
	public String generate() {
		return StringUtil.random(6, RandomType.INT);
	}
}
