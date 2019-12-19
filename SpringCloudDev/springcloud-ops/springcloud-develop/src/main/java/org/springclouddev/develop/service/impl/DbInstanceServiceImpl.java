
package org.springclouddev.develop.service.impl;

import org.springclouddev.develop.entity.DbInstance;
import org.springclouddev.develop.vo.DbInstanceVO;
import org.springclouddev.develop.mapper.DbInstanceMapper;
import org.springclouddev.develop.service.IDbInstanceService;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;

/**
 *  服务实现类
 *
 * @author zhaobohao
 * @since 2019-12-17
 */
@Service
public class DbInstanceServiceImpl extends BaseServiceImpl<DbInstanceMapper, DbInstance> implements IDbInstanceService {

	@Override
	public IPage<DbInstanceVO> selectDbInstancePage(IPage<DbInstanceVO> page, DbInstanceVO dbInstance) {
		return page.setRecords(baseMapper.selectDbInstancePage(page, dbInstance));
	}

}
