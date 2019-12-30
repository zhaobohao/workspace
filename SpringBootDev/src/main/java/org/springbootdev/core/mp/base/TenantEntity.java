
package org.springbootdev.core.mp.base;


import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

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
}
