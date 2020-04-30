package org.springclouddev.integral.service.actRule;


import org.springclouddev.core.mp.base.BaseService;
import org.springclouddev.integral.entity.ActRuleInfo;

import java.util.List;

public interface IActRuleService extends BaseService<ActRuleInfo> {
	
	List<ActRuleInfo> queryActRuleByActCode(String actCode);
    
    int  deleteActRuleBy(String actCode);
    
    int  insertBatchActRule(List<ActRuleInfo> list);


}
