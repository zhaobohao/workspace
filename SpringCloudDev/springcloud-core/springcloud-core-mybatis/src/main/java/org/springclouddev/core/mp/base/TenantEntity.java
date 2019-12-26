
package org.springclouddev.core.mp.base;


/**
 * 租户基础实体类
 *
 * @author zhaobohao
 */
public interface TenantEntity   {
	/**
	 * 获取租户的tenantId值
	 * @return
	 */
	public String getTenantId();

	/**
	 * 设置租户的tenantId
	 * @param tenantId
	 */
	public void setTenantId(String tenantId);
}
