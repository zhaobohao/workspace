package org.springclouddev.integral.service.config;

import com.googlecode.aviator.AviatorEvaluator;
import org.springclouddev.integral.aviator.util.InitFunction;
import org.springclouddev.integral.entity.ActRuleInfo;
import org.springclouddev.integral.entity.RuleExp;
import org.springclouddev.integral.mapper.ActRuleInfoMapper;
import org.springclouddev.integral.service.rule.IRuleExecCompute;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.stream.Collectors;

@Component
public class ActRuleConfigCache {

	@Autowired
	IRuleExecCompute ruleExecCompute;
	
	@Autowired
	ActRuleInfoMapper actRuleInfoMapper;
	
	@Autowired
	InitFunction init;
	
//	private static Lock lock = new ReentrantLock();
//	private static ReentrantReadWriteLock readWriteLock = new ReentrantReadWriteLock();
//	private static Lock writeLock = readWriteLock.writeLock();
//	private static Lock readLock = readWriteLock.readLock();
	
	private static final String RULE_CONFIG = "RULE_CONFIG";
	private static final String ACT_RULE_CONFIG = "ACT_RULE_CONFIG";
	
	private ConcurrentMap<String, Map<Long, RuleExp>> RULE_CONFIG_MAP = new ConcurrentHashMap<String, Map<Long, RuleExp>>();
	private ConcurrentMap<String, Map<String, List<ActRuleInfo>>> ACT_RULE_CONFIG_MAP = new ConcurrentHashMap<String, Map<String, List<ActRuleInfo>>>();
	
	public Map<Long, RuleExp> getRuleConfigMap(){
		return RULE_CONFIG_MAP.get(RULE_CONFIG);
	}
	
	public Map<String, List<ActRuleInfo>> getActRuleConfigMap(){
		return ACT_RULE_CONFIG_MAP.get(ACT_RULE_CONFIG);
	}
	
	public ActRuleConfigCache() {
		
	}
	
	/**
	 * 初始化加载act、rule配置信息
	 */
	@PostConstruct
	public void run() throws Exception {
		init.init();
		initOrUpdRuleConfig(true);
		initOrUpdActRuleConfig();

		
	}
	
	public void initOrUpdActRuleConfig() {
		List<ActRuleInfo> selectByExample = actRuleInfoMapper.selectAllActRuleInfo();;
		Map<String, List<ActRuleInfo>> collect = selectByExample.stream().collect(Collectors.groupingBy(ActRuleInfo::getActCode));
		ACT_RULE_CONFIG_MAP.put(ACT_RULE_CONFIG, collect);
		System.out.println(collect);
	}
	
	/**
	 * 初始化规则配置信息
	 */
	public void initOrUpdRuleConfig(boolean flag) {

		List<RuleExp> ruleExecAllList = ruleExecCompute.getRuleExecAllList();
		if(ruleExecAllList == null || ruleExecAllList.size() <= 0) {
			return;
		}
		Map<Long, RuleExp> ruleExecMap = ruleExecAllList.stream().collect(Collectors.toMap(RuleExp::getId, a -> a,(k1,k2)->k1));

		if(flag) {
			ruleExecAllList.forEach(value-> AviatorEvaluator.compile(value.getExpId()));
		}
		RULE_CONFIG_MAP.put(RULE_CONFIG, ruleExecMap);	
		
		System.out.println(RULE_CONFIG_MAP.get(RULE_CONFIG).size());
	}
	
	/**
	 * 初始化加载aviator自定义方法
	 */
//	private void initFunction() {
//		AviatorEvaluator.addFunction(new ThisMonthFunction());
//		AviatorEvaluator.addFunction(new ThisTodayFunction());
//		AviatorEvaluator.addFunction(new ThisWeekFunction());
//		AviatorEvaluator.addFunction(new IntegralMultiplication());
//		AviatorEvaluator.addFunction(new IntegralSubtraction());
////		AviatorEvaluator.addFunction(new IntegralDivision());
//	}
}
