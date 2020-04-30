package org.springclouddev.integral.mapper;

import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.core.mp.base.SuperMapper;
import org.springclouddev.integral.entity.IntegralAdjustAction;

import java.util.List;

/**
 * 积分调整动作表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface IntegralAdjustActionMapper extends SuperMapper<IntegralAdjustAction> {

    /**
     * 自定义分页
     *
     * @param page
     * @param integralAdjustAction
     * @return
     */
    List<IntegralAdjustAction> selectIntegralAdjustActionPage(IPage page, IntegralAdjustAction integralAdjustAction);
}
