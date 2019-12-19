
package org.springbootdev.modules.develop.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.BaseService;
import org.springbootdev.modules.develop.entity.DbInstance;
import org.springbootdev.modules.develop.vo.DbInstanceVO;

/**
 *  服务类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
public interface IDbInstanceService extends BaseService<DbInstance> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param dbInstance
	 * @return
	 */
	IPage<DbInstanceVO> selectDbInstancePage(IPage<DbInstanceVO> page, DbInstanceVO dbInstance);

}
