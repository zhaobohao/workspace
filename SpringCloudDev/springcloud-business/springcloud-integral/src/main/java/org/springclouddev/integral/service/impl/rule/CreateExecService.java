package org.springclouddev.integral.service.impl.rule;

import org.springclouddev.integral.aviator.model.TagIntegralResult;
import org.springclouddev.integral.aviator.model.TagParameterBean;
import org.springclouddev.integral.aviator.util.CreateExecUtil;
import org.springclouddev.integral.entity.RuleExp;
import org.springclouddev.integral.mapper.RuleExpMapper;
import org.springclouddev.integral.mapper.RuleInfoMapper;
import org.springclouddev.integral.service.config.ActRuleConfigCache;
import org.springclouddev.integral.service.rule.ICreateExec;
import org.springclouddev.integral.utils.UnparsedData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Service
public class CreateExecService implements ICreateExec {

	ActRuleConfigCache actRuleConfigCache;

	@Autowired
	RuleInfoMapper ruleInfoMapper;
	
	@Autowired
	RuleExpMapper ruleExpMapper;
	
	@Override
	public void insertRuleMsg(Long ruleId) {

		String exp = ruleInfoMapper.selectById(ruleId).getRuleExp();
		
		List<TagParameterBean> unparsedData = UnparsedData.getUnparsedData(exp);
		TagIntegralResult calculateData = UnparsedData.getCalculateData(exp);
		insertRuleMsg(ruleId, unparsedData, calculateData);
		
		
	}
	
	private void insertRuleMsg(Long ruleId, List<TagParameterBean> list,TagIntegralResult tagIntegralResult) {
		String jointExec = CreateExecUtil.getJointExec(list, tagIntegralResult);
		RuleExp ruleExp = new RuleExp();
		ruleExp.setCrtTime(LocalDateTime.now());
		ruleExp.setUpdTime(LocalDateTime.now());
		ruleExp.setVersion(System.currentTimeMillis()+"");
		ruleExp.setExpId(jointExec);
		ruleExpMapper.insert(ruleExp);
		actRuleConfigCache.initOrUpdRuleConfig(false);
	}
	
	
//	private void updateRuleMsg(RuleExp ruleExp, List<TagParameterBean> list,TagIntegralResult tagIntegralResult) {
//		String jointExec = CreateExecUtil.getJointExec(list, tagIntegralResult);
//		ruleExp.setUpdTime(new Date());
//		ruleExp.setExpId(jointExec);
//		ruleExp.setVersion(System.currentTimeMillis()+"");
//		RuleExpExample example = new RuleExpExample();
//		example.or().andRuleIdEqualTo(ruleExp.getRuleId());
//		ruleExpMapper.updateByExampleSelective(ruleExp, example);
//		RULE_CONFIG_THREAD.submit(new UpdateRuleConfigTask());
//		
//	}
	
	
//	private String getExec(TagParameterBean param) {
//		switch (param.getType()) {
//		case ComputeType.TYPE_WEEK:
//			return CreateExecUtil.createThisWeek(param.getTagName());
//		case ComputeType.TYPE_TRANS_MONTH:
//			return CreateExecUtil.createTransactionsMonth(param.getTagName(), param.getParameterOne());
//		case ComputeType.TYPE_TODAY:
//			return CreateExecUtil.createThisToday(param.getTagName());
//		case ComputeType.TYPE_RANGE:
//			return CreateExecUtil.createTwoCompare(param.getTagName(), param.getParameterOne(),
//					param.getParameterTwo());
//		case ComputeType.TYPE_MONTH:
//			return CreateExecUtil.createThisMonth(param.getTagName());
//		case ComputeType.TYPE_TAG_LARGER_PARAM:
//			return CreateExecUtil.createSmallCompare(param.getTagName(), param.getParameterOne());
//		case ComputeType.TYPE_TAG_SMALL_PARAM:
//			return CreateExecUtil.createBigCompare(param.getTagName(), param.getParameterTwo());
//		case ComputeType.TYPE_CONTAINS:
//			return CreateExecUtil.createContains(param.getTagName(), param.getParameterOne());
//		default:
//			return null;
//		}
//		
//		
//	}
	
	@Override
	public void insertActRuleRelation() {
		actRuleConfigCache.initOrUpdActRuleConfig();
	}


}
