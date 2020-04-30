package org.springclouddev.integral.service.accountintegral;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.AccountIntegralDetail;

public interface IaccountIntegralDetailService extends BaseService<AccountIntegralDetail> {
    IPage<AccountIntegralDetail> selectPageByJoinCustIntegralDetail(IPage<AccountIntegralDetail> page, QueryWrapper<AccountIntegralDetail> queryWrapper, String identyType, String identyCard) ;
}
