package org.springclouddev.integral.service.impl.ruleInfo;

import cn.hutool.core.util.StrUtil;
import com.alibaba.druid.util.StringUtils;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.integral.entity.RuleInfo;
import org.springclouddev.integral.mapper.RuleInfoMapper;
import org.springclouddev.integral.service.rule.ICreateExec;
import org.springclouddev.integral.service.ruleInfo.IRuleInfoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Service
public class RuleInfoService extends BaseServiceImpl<RuleInfoMapper, RuleInfo> implements IRuleInfoService {

    @Autowired
    ICreateExec createExec;

//	@Override
//	public List<ActPrm> getInitRuleConfig(String sysId,String depId) {
//		ActPrmCateExample actPrmCateExample = new ActPrmCateExample();
//		actPrmCateExample.or().andStaEqualTo("start_sign_start");
//		List<ActPrmCate> actPrmCate = actPrmCateMapper.selectByExample(actPrmCateExample);
//		Map<String, List<ActPrmCate>> collect = actPrmCate.stream().collect(Collectors.toMap(ActPrmCate::getApCode, 
//				value -> Lists.newArrayList(value),
//		          (List<ActPrmCate> newValueList, List<ActPrmCate> oldValueList) -> {  
//		              oldValueList.addAll(newValueList);  
//		              return oldValueList;  
//		          }));
//		ActPrmExample actPrmExample = new ActPrmExample();
//		ActPrmExample.Criteria criteria = actPrmExample.or();
//		criteria.andStaEqualTo("01");
//		if(StrUtil.isNotBlank(depId)) {
//			criteria.andHideDeptIdNotEqualTo(depId);
//		}
//		if(StrUtil.isNotBlank(sysId)) {
//			criteria.andSysIdEqualTo(sysId);
//		}
//		List<ActPrm> ActPrm = actPrmMapper.selectByExample(actPrmExample);
//		ActPrm.forEach(x->{
//			if(collect.get(x.getCode())!=null) {
//				x.setParams(collect.get(x.getCode()));
//				}
//			});
//		
//		
//		return ActPrm;
//	}

    @Override
    public List<RuleInfo> getRuleInfoList(String ruleName, int page, int limit) {
        return baseMapper.selectPage(
                Condition.getPage(new Query().setSize(limit).setCurrent(page)),
                Condition.getQueryWrapper(new RuleInfo()).lambda()
                        .like(RuleInfo::getRuleName, ruleName)
        )
                .getRecords();
    }

    @Override
    public List<RuleInfo> getRuleInfoAuditList(String ruleName, int page, int limit) {
        return baseMapper.selectPage(
                Condition.getPage(new Query().setSize(limit).setCurrent(page)),
                Condition.getQueryWrapper(
                        new RuleInfo().setRuleStatus("audit_status_wait")).lambda()
                        .like(RuleInfo::getRuleName, ruleName)
        )
                .getRecords();
    }


    @Override
    public List<RuleInfo> getRuleInfoList(String ruleName) {
        return baseMapper.selectList(
                Condition.getQueryWrapper(
                        new RuleInfo().setRuleStatus("audit_status_wait")
                ).lambda()
                        .like(RuleInfo::getRuleName, ruleName)
        );
    }

    @Override
    public boolean delRuleInfo(Long ruleId) {
        RuleInfo ruleInfo = baseMapper.selectById(ruleId);
        if (ruleInfo == null || !StringUtils.equals(ruleInfo.getRuleStatus(), "audit_status_tosub")
                || !StringUtils.equals(ruleInfo.getRuleStatus(), "audit_status_nopass")) {
            return false;
        }
        baseMapper.deleteById(ruleId);
        return true;
    }

    @Override
    public boolean pushRuleInfoAudit(RuleInfo rule) {
        RuleInfo ruleInfo = baseMapper.selectById(rule.getId());
        if (ruleInfo == null || !(StringUtils.equals(ruleInfo.getRuleStatus(), "audit_status_tosub")
                || StringUtils.equals(ruleInfo.getRuleStatus(), "audit_status_nopass"))) {
            return false;
        }
        ruleInfo.setLstUpdTime(LocalDateTime.now());
        ruleInfo.setRuleStatus("audit_status_wait");
        baseMapper.updateById(ruleInfo);
        return true;

    }

    @Override
    public void insertRuleInfo(RuleInfo rule) {
        rule.setCrtDate(LocalDateTime.now());
        rule.setRuleStatus("audit_status_tosub");
        baseMapper.insert(rule);
    }

    @Override
    public boolean updateRuleInfo(RuleInfo rule) {
        //查询审核状态
        RuleInfo ruleInfo = baseMapper.selectById(rule.getId());
        if (ruleInfo == null || !(StringUtils.equals(ruleInfo.getRuleStatus(), "audit_status_tosub")
                || StringUtils.equals(ruleInfo.getRuleStatus(), "audit_status_nopass"))) {
            return false;
        }
        ruleInfo.setLstUpdTime(LocalDateTime.now());
        baseMapper.updateById(ruleInfo);
        return true;
    }

    @Override
    public RuleInfo selectRuleInfo(Long ruleId) {
        return baseMapper.selectById(ruleId);

    }

    @Override
    @Transactional
    public boolean passedRuleInfoAudit(RuleInfo rule) {
        RuleInfo ruleInfo = baseMapper.selectById(rule.getId());
        if (ruleInfo == null || !StringUtils.equals(ruleInfo.getRuleStatus(), "audit_status_wait")) {
            return false;
        }
        ruleInfo.setLstUpdTime(LocalDateTime.now());
        baseMapper.updateById(ruleInfo);
        if (StrUtil.equals("audit_status_pass", rule.getRuleStatus())) {
            createExec.insertRuleMsg(ruleInfo.getId());
        }
        return true;
    }

    @Override
    public boolean unPassedRuleInfoAudit(RuleInfo rule) {
        RuleInfo ruleInfo = baseMapper.selectById(rule.getId());
        if (ruleInfo == null || !StringUtils.equals(ruleInfo.getRuleStatus(), "audit_status_wait")) {
            return false;
        }
        rule.setRuleStatus("audit_status_nopass");
        ruleInfo.setLstUpdTime(LocalDateTime.now());
        baseMapper.updateById(ruleInfo);
        return true;
    }

    @Override
    public boolean verufyRuleName(RuleInfo rule) {
        List<RuleInfo> selectByExample = baseMapper.selectList(Condition.getQueryWrapper(new RuleInfo().setRuleName(rule.getRuleName())));
        if (selectByExample != null && selectByExample.size() > 0) {
            return false;
        }
        return true;
    }

    @Override
    public long getStatusCount(String status) {
        return baseMapper.selectCount(Condition.getQueryWrapper(new RuleInfo().setRuleStatus(status)));
    }


}
