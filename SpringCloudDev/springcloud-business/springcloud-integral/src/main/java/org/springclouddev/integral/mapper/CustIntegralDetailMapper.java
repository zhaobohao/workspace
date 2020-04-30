package org.springclouddev.integral.mapper;

import org.apache.ibatis.annotations.Mapper;
import org.springclouddev.integral.entity.CustIntegralDetail;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 客户积分明细表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface CustIntegralDetailMapper extends SuperMapper<CustIntegralDetail> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param custIntegralDetail
	 * @return
	 */
	List<CustIntegralDetail> selectCustIntegralDetailPage(IPage page, CustIntegralDetail custIntegralDetail);
                                                                                                                                                            }
