package org.springclouddev.integral.service.custIntegral;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import org.apache.ibatis.annotations.Param;
import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.*;

import java.util.List;
import java.util.Map;

public interface ICustIntegralService extends BaseService<CustIntegralDetail> {
    List<CustIntegralDetail> getCustIntegralInfo(CustIntegralDetail example);

    List<AccountIntegralDetail> getAccountIntegralDetail(AccountIntegralDetail example);

    public List<CustIntegralDetail> selectCustIntegralDetailByExample(CustIntegralDetail example);

    public void insertIntegralAdjust(IntegralAdjust record);

    public void insertIntegralAdjustAction(IntegralAdjustAction record);

    public List<AccountIntegralDetail> selectAccountIntegralDetailByExample(AccountIntegralDetail example);

    public void updateCustIntegralDetailByExampleSelective(@Param("record") CustIntegralDetail record, @Param("example") CustIntegralDetail example);

    public void insertCustIntegralDetail(CustIntegralDetail record);

    public void insertAccountIntegralDetail(AccountIntegralDetail record);

    public List<IntegralAdjustAction> selectIntegralAdjustActionByExample(IntegralAdjustAction example);

    public void updateByExampleSelective(@Param("record") IntegralAdjustAction record, @Param("example") IntegralAdjustAction example);

    public void insertIntegralStatusAction(IntegralStatusAction record);

    public long countByIntegralStatusActionExample(IntegralStatusAction example);

    public void updateIntegralStatusActionByExampleSelective(@Param("record") IntegralStatusAction record, @Param("example") IntegralStatusAction example);

    public List<IntegralStatusAction> selectntegralStatusActionByExample(IPage page, Wrapper<IntegralStatusAction> queryWrapper);

    public int updateAccountDeatilByExampleSelective(@Param("record") AccountIntegralDetail record, @Param("example") AccountIntegralDetail example);

    List<IntegralAdjustAction> selectIntegralAdjustActionPageByExample(IPage<IntegralAdjustAction> page, QueryWrapper<IntegralAdjustAction> queryWrapper);
}
