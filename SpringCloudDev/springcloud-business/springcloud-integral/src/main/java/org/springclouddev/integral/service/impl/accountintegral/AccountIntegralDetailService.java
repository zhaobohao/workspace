package org.springclouddev.integral.service.impl.accountintegral;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.integral.entity.AccountIntegralDetail;
import org.springclouddev.integral.mapper.AccountIntegralDetailMapper;
import org.springclouddev.integral.service.accountintegral.IaccountIntegralDetailService;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
@Slf4j
public class AccountIntegralDetailService extends BaseServiceImpl<AccountIntegralDetailMapper, AccountIntegralDetail> implements IaccountIntegralDetailService {

    @Override
    public IPage<AccountIntegralDetail> selectPageByJoinCustIntegralDetail(IPage<AccountIntegralDetail> page, QueryWrapper<AccountIntegralDetail> queryWrapper, String identyType, String identyCard) {
        if (!StringUtils.isEmpty(identyType)) {
            queryWrapper.eq("cust.IDENTY_TYPE", identyType);
        }
        if (!StringUtils.isEmpty(identyCard)) {
            queryWrapper.eq("cust.IDENTY_CARD", identyCard);
        }
        return this.getBaseMapper().selectPageByJoinCustIntegralDetail(page, queryWrapper);
    }
}
