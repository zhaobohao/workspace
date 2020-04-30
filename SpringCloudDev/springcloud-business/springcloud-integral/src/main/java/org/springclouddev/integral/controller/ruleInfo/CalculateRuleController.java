package org.springclouddev.integral.controller.ruleInfo;

import cn.hutool.core.util.StrUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.integral.aviator.common.ActResult;
import org.springclouddev.integral.aviator.common.RuleResult;
import org.springclouddev.integral.enums.ReturnCode;
import org.springclouddev.integral.service.rule.IRuleExecCompute;
import org.springclouddev.integral.vo.ExpectAclVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;


@Api("规则计算")
@RestController
@RequestMapping("/ruleCal")
public class CalculateRuleController {

	private static Logger logger = LoggerFactory.getLogger(CalculateRuleController.class);

	@Autowired
	private IRuleExecCompute RuleExecCompute;
	
	
	@ApiOperation("规则预计算")
	@PostMapping("/expectCal")
	public Object ruleExpectCal(@RequestBody ExpectAclVo userMsg) {
		try {
			List<Object> list = new ArrayList<Object>();
			if(userMsg.getRuleId() != null && userMsg.getRuleId() != 0) {
				RuleResult result = RuleExecCompute.getRuleExecPrecomputedResultByRuleId(userMsg.getRuleId(), userMsg.getUserMsg());
				list.add(result);
			}
			if(StrUtil.isNotBlank(userMsg.getActId())) {
				ActResult result = RuleExecCompute.getRuleExecPrecomputedResultByActId(userMsg.getActId(), userMsg.getUserMsg());
				list.add(result);
			}
			if((userMsg.getRuleId() == null || userMsg.getRuleId() == 0)
					&& StrUtil.isBlank(userMsg.getActId())){
				//查询所有规则
				list.addAll(RuleExecCompute.getRuleExecPrecomputedResultRuleId(userMsg.getUserMsg()));
			}
			return R.data(list);
		} catch (Exception e) {
			logger.error("预计算异常"+e);
			return R.fail(ReturnCode.JSON_ERROR);
		}
	}
	
	
}
