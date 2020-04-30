package org.springclouddev.integral.mapper;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springclouddev.integral.entity.AccountIntegralDetail;
import org.springclouddev.core.mp.base.SuperMapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import java.util.List;

/**
 * 账户积分交易明细表 Mapper 接口
 *
 * @author zhaobohao
 * @since 2020-01-15
 */
@Mapper
public interface AccountIntegralDetailMapper extends SuperMapper<AccountIntegralDetail> {

	/**
	 * 自定义分页
	 *
	 * @param page
	 * @param accountIntegralDetail
	 * @return
	 */
	List<AccountIntegralDetail> selectAccountIntegralDetailPage(IPage page, AccountIntegralDetail accountIntegralDetail);

	@Select("select * from tbl_account_integral_detail account join tbl_cust_integral_detail cust  on account.ACCOUNT_ID=cust.ACCOUNT_ID ${ew.getCustomSqlSegment} ")
	IPage<AccountIntegralDetail> selectPageByJoinCustIntegralDetail(IPage<AccountIntegralDetail> page, @Param("ew")QueryWrapper<AccountIntegralDetail> queryWrapper);
}
