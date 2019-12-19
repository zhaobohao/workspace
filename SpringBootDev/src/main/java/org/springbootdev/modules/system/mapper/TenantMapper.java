
package org.springbootdev.modules.system.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.SuperMapper;
import org.springbootdev.modules.system.entity.Tenant;

import java.util.List;

/**
 *  Mapper 接口
 *
 * @author zhaobohao
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
