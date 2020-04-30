package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.IntegralAct;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 积分活动管理表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface IntegralActMapper extends SuperMapper<IntegralAct> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param integralAct
	 * @return
	 */
	List<IntegralAct> selectIntegralActPage(IPage page, IntegralAct integralAct);
                                                                                                                                                                                            }
