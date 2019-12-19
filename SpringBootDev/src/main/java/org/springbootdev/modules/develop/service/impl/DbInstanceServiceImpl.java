
package org.springbootdev.modules.develop.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springbootdev.core.mp.base.BaseServiceImpl;
import org.springbootdev.modules.develop.entity.DbInstance;
import org.springbootdev.modules.develop.mapper.DbInstanceMapper;
import org.springbootdev.modules.develop.service.IDbInstanceService;
import org.springbootdev.modules.develop.vo.DbInstanceVO;
import org.springframework.stereotype.Service;

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
