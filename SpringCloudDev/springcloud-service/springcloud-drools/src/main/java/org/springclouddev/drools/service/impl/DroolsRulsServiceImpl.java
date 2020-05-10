package org.springclouddev.drools.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.drools.entity.DroolsRuls;
import org.springclouddev.drools.mapper.DroolsRulsMapper;
import org.springclouddev.drools.service.IDroolsRulsService;
import org.springclouddev.drools.vo.DroolsRulsVO;
import org.springframework.stereotype.Service;

/**
 * 服务实现类
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
@Service
@Slf4j
public class DroolsRulsServiceImpl extends BaseServiceImpl<DroolsRulsMapper, DroolsRuls> implements IDroolsRulsService {

    @Override
    public IPage<DroolsRulsVO> selectDroolsRulsPage(IPage<DroolsRulsVO> page, DroolsRulsVO droolsRuls) {
        return page.setRecords(baseMapper.selectDroolsRulsPage(page, droolsRuls));
    }

}
