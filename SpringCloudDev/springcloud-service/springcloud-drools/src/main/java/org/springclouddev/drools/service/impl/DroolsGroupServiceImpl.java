package org.springclouddev.drools.service.impl;

import org.springclouddev.drools.entity.DroolsGroup;
import org.springclouddev.drools.vo.DroolsGroupVO;
import org.springclouddev.drools.mapper.DroolsGroupMapper;
import org.springclouddev.drools.service.IDroolsGroupService;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
/**
 *  服务实现类
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
@Service
@Slf4j
public class DroolsGroupServiceImpl extends BaseServiceImpl<DroolsGroupMapper, DroolsGroup> implements IDroolsGroupService {

	@Override
	public IPage<DroolsGroupVO> selectDroolsGroupPage(IPage<DroolsGroupVO> page, DroolsGroupVO droolsGroup) {
		return page.setRecords(baseMapper.selectDroolsGroupPage(page, droolsGroup));
	}
                                                    
}
