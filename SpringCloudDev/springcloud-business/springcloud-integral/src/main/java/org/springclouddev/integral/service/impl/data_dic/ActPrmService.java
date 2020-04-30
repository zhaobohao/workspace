package org.springclouddev.integral.service.impl.data_dic;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.integral.entity.ActPrm;
import org.springclouddev.integral.mapper.ActPrmMapper;
import org.springclouddev.integral.service.data_dic.IActPrmService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Slf4j
public class ActPrmService extends BaseServiceImpl<ActPrmMapper, ActPrm> implements IActPrmService {


    @Override
    public List<ActPrm> qryActPrms(ActPrm actPrm, int pageNum, int pageSize) {
        String code = actPrm.getCode();
        String name = actPrm.getName();
        String parentCodeId = actPrm.getParentCoderId();
        String sysId = actPrm.getSysId();
        Map<String, Object> example = new HashMap<>();
        example.put("parentCoderId", parentCodeId);
        example.put("sysId", sysId);
        example.put("code_like", code);
        example.put("name_like", name);
        Query query = new Query();
        query.setCurrent(pageNum);
        query.setSize(pageSize);
        query.setDescs("AP_order");
        return baseMapper.selectPage(Condition.getPage(query), Condition.getQueryWrapper(example, ActPrm.class)).getRecords();
    }

    @Override
    public ActPrm qryActPrmById(String code) {
        return baseMapper.selectOne(Condition.getQueryWrapper(new ActPrm().setCode(code)));
    }

    @Override
    public int crtActPrm(ActPrm sysActPrm) {
        return baseMapper.insert(sysActPrm);
    }

    @Override
    public int updActPrm(ActPrm actPrm) {
        return baseMapper.updateById(actPrm);
    }

    @Override
    @Transactional
    public boolean delActPrm(Long id) {
        if (baseMapper.deleteById(id) > 0) {
            return true;
        }
        return false;
    }

    @Override
    public List<ActPrm> qryAllStartActPrms() {
        return baseMapper.selectList(Condition.getQueryWrapper(new ActPrm().setStat("start_sign_start")));
    }


}
