
package org.springclouddev.integral.wrapper;

import org.springclouddev.core.mp.support.BaseEntityWrapper;
import org.springclouddev.integral.entity.CustIntegralDetail;
import org.springclouddev.integral.entity.IntegralAct;
import org.springclouddev.integral.entity.IntegralAdjustAction;
import org.springclouddev.integral.service.config.LocalDictCache;
import org.springclouddev.integral.vo.ActIntegralInfoVo;
import org.springclouddev.integral.vo.AdjustIntegralVo;

import java.time.ZoneId;
import java.util.Date;

/**
 * 行政区划包装类,返回视图层所需的字段
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
public class ActIntegralInfoWrapper extends BaseEntityWrapper<IntegralAct, ActIntegralInfoVo>  {

    public static ActIntegralInfoWrapper build() {
        return new ActIntegralInfoWrapper();
    }

	@Override
	public ActIntegralInfoVo entityVO(IntegralAct actIntegralInfo) {
		ActIntegralInfoVo actIntegralInfoVo  = new ActIntegralInfoVo();
		actIntegralInfoVo.setActCode(actIntegralInfo.getActCode());
		actIntegralInfoVo.setActName(actIntegralInfo.getActName());
		actIntegralInfoVo.setMarketActId(actIntegralInfo.getMarketActId());
		actIntegralInfoVo.setDepartment(actIntegralInfo.getDepartment());
		actIntegralInfoVo.setIntegralType(actIntegralInfo.getIntegralType());
		actIntegralInfoVo.setIntegralLimitTimeType(Integer.parseInt(actIntegralInfo.getIntegralLimitTimeType()));
		actIntegralInfoVo.setIntegralLimitYearNum(String.valueOf(actIntegralInfo.getIntegralLimitYearNum()));
		if("1".equals(actIntegralInfo.getIntegralLimitTimeType())) {
			actIntegralInfoVo.setIntegralLimitYearNum("永久有效");
		}
		actIntegralInfoVo.setIntegralEndMonth(actIntegralInfo.getIntegralEndMonth());
		actIntegralInfoVo.setPrepareCount(actIntegralInfo.getPrepareCount());
		actIntegralInfoVo.setPrepareIntegralNum(actIntegralInfo.getPrepareIntegralNum());
		actIntegralInfoVo.setActBreif(actIntegralInfo.getActBreif());
		actIntegralInfoVo.setRuleTeam(actIntegralInfo.getRuleTeam());
		actIntegralInfoVo.setStatus(actIntegralInfo.getState());
		actIntegralInfoVo.setCrtUser(String.valueOf(actIntegralInfo.getCreateUser()));
		actIntegralInfoVo.setCrtTime(Date.from( actIntegralInfo.getCreateTime().atZone( ZoneId.systemDefault()).toInstant())
);
		actIntegralInfoVo.setLstUpdUser(String.valueOf(actIntegralInfo.getUpdateUser()));
		actIntegralInfoVo.setLstUpdTime(Date.from( actIntegralInfo.getUpdateTime().atZone( ZoneId.systemDefault()).toInstant()));
		actIntegralInfoVo.setStatusName(LocalDictCache.getDicNameById(""+actIntegralInfo.getStatus()));

		return actIntegralInfoVo;
	}
}
