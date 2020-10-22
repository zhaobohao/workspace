package org.springclouddev.system.mapper;

import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.system.entity.Tenant;

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
