package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.IntegralStatusAction;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 积分冻结解冻状态动作表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface IntegralStatusActionMapper extends SuperMapper<IntegralStatusAction> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param integralStatusAction
	 * @return
	 */
	List<IntegralStatusAction> selectIntegralStatusActionPage(IPage page, IntegralStatusAction integralStatusAction);
                                                                                                            }
