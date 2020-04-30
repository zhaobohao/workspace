package org.springclouddev.integral.service.impl.actRule;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.integral.entity.ActRuleInfo;
import org.springclouddev.integral.mapper.ActRuleInfoMapper;
import org.springclouddev.integral.mapper.RuleInfoMapper;
import org.springclouddev.integral.service.actRule.IActRuleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Slf4j
public class ActRuleService extends BaseServiceImpl<ActRuleInfoMapper, ActRuleInfo> implements IActRuleService {

    @Override
    public List<ActRuleInfo> queryActRuleByActCode(String actCode) {
        ActRuleInfo example = new ActRuleInfo();
        example.setActCode(actCode);
        List<ActRuleInfo> list = null;
        try {
            list = baseMapper.selectList(Condition.getQueryWrapper(example));
        } catch (Exception e) {
            log.error("数据库查询数据异常，" + e.getMessage());
            return list;
        }

        return list;
    }

    /**
     * 根据actCode删除绑定该actCode的规则
     */
    @Override
    public int deleteActRuleBy(String actCode) {
        ActRuleInfo example = new ActRuleInfo();
        example.setActCode(actCode);
        int resultCode = -1;
        try {
            resultCode = baseMapper.delete(Condition.getQueryWrapper(example));
        } catch (Exception e) {
            log.error("删除数据异常，" + e.getMessage());
            return -1;
        }
        return resultCode;
    }


    /**
     * 插入积分活动编号绑定的规则盗数据库，插入之前，先删除原本的
     */
    @Override
    @Transactional
    public int insertBatchActRule(List<ActRuleInfo> list) {
        if (list == null && list.size() == 0) {
            return -1;
        }
        ActRuleInfo example = new ActRuleInfo();
        example.setActCode(list.get(0).getActCode());
        try {
            baseMapper.delete(Condition.getQueryWrapper(example));
            this.saveBatch(list);
        } catch (Exception e) {
            log.error("数据插入数据库失败，" + e.getMessage());
            return -1;
        }

        return 0;
    }
}
