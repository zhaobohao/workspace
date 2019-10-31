
package org.springbootdev.modules.system.mapper;

import org.springbootdev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.modules.system.entity.Tenant;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author merryChen
 */
public interface TenantMapper extends SuperMapper<Tenant> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param tenant
	 * @return
	 */
	List<Tenant> selectTenantPage(IPage page, Tenant tenant);

}
