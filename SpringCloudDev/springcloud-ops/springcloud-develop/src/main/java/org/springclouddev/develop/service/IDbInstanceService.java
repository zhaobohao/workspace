
package org.springclouddev.develop.service;

import org.springclouddev.develop.entity.DbInstance;
import org.springclouddev.develop.vo.DbInstanceVO;
import org.springclouddev.core.mp.base.BaseService;
import com.baomidou.mybatisplus.core.metadata.IPage;

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
