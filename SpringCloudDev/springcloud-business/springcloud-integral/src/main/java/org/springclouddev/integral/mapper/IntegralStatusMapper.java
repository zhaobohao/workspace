package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.IntegralStatus;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 积分冻结解冻状态表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface IntegralStatusMapper extends SuperMapper<IntegralStatus> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param integralStatus
	 * @return
	 */
	List<IntegralStatus> selectIntegralStatusPage(IPage page, IntegralStatus integralStatus);
                                                            }
