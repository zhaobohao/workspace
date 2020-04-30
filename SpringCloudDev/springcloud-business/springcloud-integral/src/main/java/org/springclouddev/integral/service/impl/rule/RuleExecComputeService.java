package org.springclouddev.integral.service.impl.rule;

import com.alibaba.fastjson.JSONObject;
import com.googlecode.aviator.AviatorEvaluator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.integral.aviator.common.ActResult;
import org.springclouddev.integral.aviator.common.ComputeType;
import org.springclouddev.integral.aviator.common.ReturnMsg;
import org.springclouddev.integral.aviator.common.RuleResult;
import org.springclouddev.integral.aviator.util.CreateExecUtil;
import org.springclouddev.integral.entity.ActRuleInfo;
import org.springclouddev.integral.entity.RuleExp;
import org.springclouddev.integral.mapper.RuleExpMapper;
import org.springclouddev.integral.service.config.ActRuleConfigCache;
import org.springclouddev.integral.service.rule.IRuleExecCompute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class RuleExecComputeService implements IRuleExecCompute {

	private static final Logger log = LoggerFactory.getLogger(RuleExecComputeService.class);

	@Autowired
	RuleExpMapper ruleExpMapper;

	@Autowired
	ActRuleConfigCache actRuleConfigCache;

	@Override
	public RuleResult getRuleExecPrecomputedResultByRuleId(Long ruleId, JSONObject userMsg) {
		Map<Long, RuleExp> ruleConfigMap = actRuleConfigCache.getRuleConfigMap();
		if (ruleConfigMap == null || ruleConfigMap.size() <= 0) {
			return new RuleResult(ReturnMsg.RULE_LIST_NULL.getCode(), ReturnMsg.RULE_LIST_NULL.getMsg(), 0);
		}
		return getRuleResult(ruleId, userMsg, ruleConfigMap);
	}

	private RuleResult getRuleResult(Long ruleId, JSONObject userMsg, Map<Long, RuleExp> ruleConfigMap) {
		RuleExp ruleExp = ruleConfigMap.get(ruleId);
		long execute = 0;
		try {
			execute = Long.parseLong(AviatorEvaluator.execute(ruleExp.getExpId(), userMsg) + "");
			return new RuleResult(ReturnMsg.SUCCESS.getCode(), ReturnMsg.SUCCESS.getMsg(), execute, ruleId,
					ruleExp.getRuleName());
		} catch (Exception e) {
			e.printStackTrace();
			log.error("计算信息异常" + e);
			return new RuleResult(ReturnMsg.EXEC_ERROR.getCode(), ReturnMsg.EXEC_ERROR.getMsg(), 0, ruleId,
					ruleExp.getRuleName());
		}
	}

	@Override
	public ActResult getRuleExecPrecomputedResultByActId(String actId, JSONObject userMsg) {
		long execute = 0;
		Map<Long, RuleResult> map = new HashMap<Long, RuleResult>();
		List<ActRuleInfo> list = actRuleConfigCache.getActRuleConfigMap().get(actId);
		Map<Long, RuleExp> ruleConfigMap = actRuleConfigCache.getRuleConfigMap();
		if (ruleConfigMap == null || ruleConfigMap.size() <= 0) {
			return new ActResult(ReturnMsg.RULE_LIST_NULL.getCode(), ReturnMsg.RULE_LIST_NULL.getMsg(), 0);
		}
		try {
			String ruleTeam = list.get(0).getRuleTeam();
			list.stream().map(actRuleInfo -> actRuleInfo.getRuleId())
					.forEach(value -> map.put(value, getRuleResult(value, userMsg, ruleConfigMap)));

			List<RuleResult> collect = map.values().stream().filter(CreateExecUtil::removeResultIsZero)
					.collect(Collectors.toList());

			switch (ruleTeam) {
			case ComputeType.ADD:
				execute = map.entrySet().stream().map(x -> x.getValue()).mapToLong(RuleResult::getResult).sum();
				break;
			case ComputeType.MAX:
				execute = map.entrySet().stream().map(x -> x.getValue()).mapToLong(RuleResult::getResult).max()
						.getAsLong();
				break;
			case ComputeType.MIN:
				execute = map.entrySet().stream().map(x -> x.getValue()).filter(CreateExecUtil::removeResultIsZero)
						.mapToLong(RuleResult::getResult).min().getAsLong();
				break;
			case ComputeType.GRADE:
				execute = list.stream().map(x -> map.get(x.getRuleId())).filter(CreateExecUtil::removeResultIsZero)
						.mapToLong(RuleResult::getResult).findFirst().getAsLong();
				break;
			default:
				break;
			}
			return new ActResult(ReturnMsg.SUCCESS.getCode(), ReturnMsg.SUCCESS.getMsg(), execute, actId,
					list.get(0).getActName(), collect);
		} catch (Exception e) {
			log.error("计算信息异常" + e);
			return new ActResult(ReturnMsg.EXEC_ERROR.getCode(), ReturnMsg.EXEC_ERROR.getMsg(), 0, actId,
					list.get(0).getActName(), null);
		}
	}

	@Override
	public List<RuleResult> getRuleExecPrecomputedResultRuleId(JSONObject userMsg) {
		// 全量规则计算
		Map<Long, RuleExp> ruleConfigMap = actRuleConfigCache.getRuleConfigMap();
		List<RuleResult> collect = ruleConfigMap.keySet().stream()
				.map(value -> getRuleResult(value, userMsg, ruleConfigMap))
				.filter(CreateExecUtil::removeResultIsZero)
				.collect(Collectors.toList());

		return collect;
	}

	@Override
	public List<RuleExp> getRuleExecAllList() {
		return ruleExpMapper.selectAllRuleExp();
	}

}
