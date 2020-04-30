package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.IntegralAdjust;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 积分调整表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface IntegralAdjustMapper extends SuperMapper<IntegralAdjust> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param integralAdjust
	 * @return
	 */
	List<IntegralAdjust> selectIntegralAdjustPage(IPage page, IntegralAdjust integralAdjust);
                                                                                                            }
