package org.springclouddev.integral.service.actIntegral;

import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.IntegralAct;

import java.util.List;

public interface IintegralActService extends BaseService<IntegralAct> {
	
	List<IntegralAct>  queryByActCode(String actCode);
	
	List<IntegralAct>  queryAllAct();
	
	List<IntegralAct> queryByActName(String name);
	
	String updateActivity(IntegralAct actIntegralInfo);
	
	String deleteByActCode(String ActCode);
	
	String insertIntegralAct(IntegralAct actIntegralInfo);
	
	String pushToAudit(String ActCode);

	String auditPass(IntegralAct actIntegralInfo);

	List<IntegralAct>  findPage(int page, int limit, String actCode, String actName);

//	String auditReject(ActIntegralInfo actIntegralInfo);

	String getBackActintegral(IntegralAct actIntegralInfo);

	List<IntegralAct> queryAboutAduit(int page, int limit, String actCode, String actName);
	
	long getStatusCount(String status);
	

	
	
	
	
	
}
