
package org.springclouddev.core.boot.tenant;

/**
 * 租户id生成器
 *
 * @author firewan
 */
public interface TenantId {

	/**
	 * 生成自定义租户id
	 *
	 * @return string
	 */
	String generate();

}
