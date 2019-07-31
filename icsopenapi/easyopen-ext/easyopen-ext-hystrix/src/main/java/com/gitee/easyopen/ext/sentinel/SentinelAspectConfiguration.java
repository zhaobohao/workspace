package com.gitee.easyopen.ext.sentinel;

import com.alibaba.csp.sentinel.slots.block.RuleConstant;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRule;
import com.alibaba.csp.sentinel.slots.block.authority.AuthorityRuleManager;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRule;
import com.alibaba.csp.sentinel.slots.block.degrade.DegradeRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.FlowRuleManager;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowItem;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRule;
import com.alibaba.csp.sentinel.slots.block.flow.param.ParamFlowRuleManager;
import com.alibaba.csp.sentinel.slots.system.SystemRule;
import com.alibaba.csp.sentinel.slots.system.SystemRuleManager;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author zhaobo
 */
//@Configuration
public class SentinelAspectConfiguration {

    static String KEY = "资源名称";

    //@Bean
    public SentinelResourceAspect sentinelResourceAspect() {
        return new SentinelResourceAspect();
    }

    /**
     * 当资源的平均响应时间超过阈值（DegradeRule 中的 count，以 ms 为单位）之后，资源进入准降级状态。
     * 接下来如果持续进入 5 个请求，它们的 RT 都持续超过这个阈值，
     * 那么在接下的时间窗口（DegradeRule 中的 timeWindow，以 s 为单位）之内，
     * 对这个方法的调用都会自动地返回（抛出 DegradeException）。
     */
    private static void initDegradeRuleRT() {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        // 设置资源名，资源名是限流规则的作用对象
        rule.setResource(KEY);
        // 设置平均响应时间阈值为10ms
        rule.setCount(1000);
        // 设置降级模式，根据平均响应时间降级
        rule.setGrade(RuleConstant.DEGRADE_GRADE_RT);
        // 设置降级的时间，以s为单位
        rule.setTimeWindow(20);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }


    /**
     * 当资源的每秒异常总数占通过量的比值超过阈值（DegradeRule 中的 count）之后，
     * 资源进入降级状态，即在接下的时间窗口（DegradeRule 中的 timeWindow，以 s 为单位）之内，对这个方法的调用都会自动地返回。
     * 异常比率的阈值范围是 [0.0, 1.0]，代表 0% - 100%。
     */
    private static void initDegradeRuleExceptionRatio() {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        rule.setResource(KEY);// 设置资源名，资源名是限流规则的作用对象
        // 将限制异常比率设置为0.1
        rule.setCount(0.5);// 异常比率的阈值范围是 [0.0, 1.0]，代表 0% - 100%。
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_RATIO);// 设置降级模式，根据异常比率降级
        rule.setTimeWindow(10);// 设置降级的时间，以s为单位
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }


    /**
     * 当资源近 1 分钟的异常数目超过阈值之后会进行熔断
     * 当通过{@link RuleConstant#DEGRADE_GRADE_EXCEPTION_COUNT},进行降级时，时间窗口少于60秒将无法正常工作。
     * 因为异常计数是按分钟求和的，所以在较短的时间窗口过后，仍然可以满足降级条件。
     * 如在10秒内已经达到降级阈值，且加上setTimeWindow设置的降级时间仍未超过一分钟的，
     * 则在一分钟内仍然满足降级条件，setTimeWindow设置的降级时间不生效，需等待满一分钟时间后才能恢复服务。
     */
    private static void initDegradeRuleExceptionCount() {
        List<DegradeRule> rules = new ArrayList<DegradeRule>();
        DegradeRule rule = new DegradeRule();
        // 设置资源名，资源名是限流规则的作用对象
        rule.setResource(KEY);
        // 设置限流阈值，将异常计数限制为800
        rule.setCount(800);
        // 设置降级模式，根据异常计数降级
        rule.setGrade(RuleConstant.DEGRADE_GRADE_EXCEPTION_COUNT);
        // 设置降级的时间，以s为单位
        rule.setTimeWindow(10);
        rules.add(rule);
        DegradeRuleManager.loadRules(rules);
    }

    //流量控制
    private static void initFlowQpsRule() {
        List<FlowRule> rules = new ArrayList<>();
        FlowRule rule1 = new FlowRule();
        //资源名，资源名是限流规则的作用对象
        rule1.setResource(KEY);
        // 限流阈值 set limit qps to 20
        rule1.setCount(20);
        //限流阈值类型，是按照 QPS 还是线程数
        rule1.setGrade(RuleConstant.FLOW_GRADE_QPS);
        //是否根据调用者来限流
        rule1.setLimitApp("default");
        //发生拦截后的流量整形和控制策略（直接拒绝 / 排队等待 / 慢启动模式）
        rule1.setControlBehavior(RuleConstant.CONTROL_BEHAVIOR_DEFAULT);
        rules.add(rule1);
        FlowRuleManager.loadRules(rules);
    }

    //系统保护规则
    private void initSystemProtectionRule() {
        List<SystemRule> rules = new ArrayList<>();
        SystemRule rule = new SystemRule();
        //最大的 load1
        rule.setHighestSystemLoad(10);
        //所有入口流量的平均响应时间
        rule.setAvgRt(2000);
        //入口流量的最大并发数
        rule.setMaxThread(200);
        //最大的qps
        rule.setQps(20000);
        rules.add(rule);
        SystemRuleManager.loadRules(rules);
    }

    //授权规则
    //调用者信息，通过 ContextUtil.enter(resourceName,origin)中的origin参数传入
    private void initAuthorityRule() {

        AuthorityRule rule = new AuthorityRule();
        rule.setResource("test");
        //白名单 ，还是黑名单，（只有在白名单里的通过，黑名单里的都 不通过）
        rule.setStrategy(RuleConstant.AUTHORITY_WHITE);
        rule.setLimitApp("appA,appB");
        AuthorityRuleManager.loadRules(Collections.singletonList(rule));
    }

    //一种特殊的限流 ，根据调用的参数进行限流
    private void initParamFlowRule() {
        ParamFlowRule rule = new ParamFlowRule("resourceName")
                .setParamIdx(0)
                .setCount(5);
        // 针对 int 类型的参数 PARAM_B，单独设置限流 QPS 阈值为 10，而不是全局的阈值 5.
        ParamFlowItem item = new ParamFlowItem().setObject(String.valueOf(1))
                .setClassType(int.class.getName())
                .setCount(10);
        rule.setParamFlowItemList(Collections.singletonList(item));

        ParamFlowRuleManager.loadRules(Collections.singletonList(rule));

    }
}

