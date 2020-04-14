
package org.springclouddev.integral.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.integral.entity.CustIntegralDetail;
import org.springclouddev.integral.entity.IntegralAdjustAction;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.vo.AdjustIntegralVo;

/**
 * 行政区划包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
public class AdjustIntegralWrapper extends BaseEntityWrapper<IntegralAdjustAction, AdjustIntegralVo>  {

    public static AdjustIntegralWrapper build() {
        return new AdjustIntegralWrapper();
    }

	@Override
	public AdjustIntegralVo entityVO(IntegralAdjustAction integralAdjustAction) {
		AdjustIntegralVo adjustIntegralVo = new AdjustIntegralVo();
		adjustIntegralVo.setId(integralAdjustAction.getId().toString());
		adjustIntegralVo.setCustId(integralAdjustAction.getCustId());
		adjustIntegralVo.setAdjustNum(integralAdjustAction.getAdjustNum());
		adjustIntegralVo.setStatus(integralAdjustAction.getStatus().toString());
		adjustIntegralVo.setStatusName(LocalDictCache.getDicPrmById(integralAdjustAction.getStatus().toString()).getName());
		adjustIntegralVo.setCrtUser(integralAdjustAction.getCrtUser());
		adjustIntegralVo.setAdjustDate(integralAdjustAction.getAdjustDate());
		adjustIntegralVo.setAdjustReason(integralAdjustAction.getAdjustReason());
		adjustIntegralVo.setLstUpdUser(integralAdjustAction.getLstUpdUser());
		adjustIntegralVo.setLstUpdTime(integralAdjustAction.getLstUpdTime());

		return adjustIntegralVo;
	}
	public AdjustIntegralVo customWrapperVo(IntegralAdjustAction integralAdjustAction,String accountId,CustIntegralDetail custIntegralDetail) {
		AdjustIntegralVo adjustIntegralVo = new AdjustIntegralVo();
		adjustIntegralVo.setId(integralAdjustAction.getId().toString());
		adjustIntegralVo.setAccountId(accountId);
		adjustIntegralVo.setCustId(integralAdjustAction.getCustId());
		adjustIntegralVo.setCustName(custIntegralDetail.getCustName());
		adjustIntegralVo.setIntegralType(LocalDictCache.getDicPrmById(custIntegralDetail.getIntegralTypeId()).getName());
		adjustIntegralVo.setIntegralValidDate(custIntegralDetail.getIntegralValidDate());
		adjustIntegralVo.setAdjustNum(integralAdjustAction.getAdjustNum());
		adjustIntegralVo.setStatus(integralAdjustAction.getStatus().toString());
		adjustIntegralVo.setStatusName(LocalDictCache.getDicPrmById(integralAdjustAction.getStatus().toString()).getName());
		adjustIntegralVo.setCrtUser(integralAdjustAction.getCrtUser());
		adjustIntegralVo.setAdjustDate(integralAdjustAction.getAdjustDate());
		adjustIntegralVo.setAdjustReason(integralAdjustAction.getAdjustReason());
		adjustIntegralVo.setLstUpdUser(integralAdjustAction.getLstUpdUser());
		adjustIntegralVo.setLstUpdTime(integralAdjustAction.getLstUpdTime());

		return adjustIntegralVo;
	}
}
