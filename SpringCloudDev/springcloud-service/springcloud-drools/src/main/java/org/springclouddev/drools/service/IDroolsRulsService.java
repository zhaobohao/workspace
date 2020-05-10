package org.springclouddev.drools.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.drools.entity.DroolsRuls;
import org.springclouddev.drools.vo.DroolsRulsVO;

/**
 * 服务类
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
public interface IDroolsRulsService extends BaseService<DroolsRuls> {

    /**
     * 自定义分页
     *
     * @param page
     * @param droolsRuls
     * @return
     */
    IPage<DroolsRulsVO> selectDroolsRulsPage(IPage<DroolsRulsVO> page, DroolsRulsVO droolsRuls);
}
