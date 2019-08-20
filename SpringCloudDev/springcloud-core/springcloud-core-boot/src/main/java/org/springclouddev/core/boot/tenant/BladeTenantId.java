
package org.springclouddev.core.boot.tenant;

import org.springclouddev.core.tool.utils.RandomType;
import org.springclouddev.core.tool.utils.StringUtil;

/**
 * blade租户id生成器
 *
 * @author firewan
 */
public class BladeTenantId implements TenantId {
	@Override
	public String generate() {
		return StringUtil.random(6, RandomType.INT);
	}
}
