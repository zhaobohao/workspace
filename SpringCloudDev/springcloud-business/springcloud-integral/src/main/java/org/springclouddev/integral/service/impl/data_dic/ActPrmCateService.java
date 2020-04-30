package org.springclouddev.integral.service.impl.data_dic;

import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.integral.entity.ActPrmCate;
import org.springclouddev.integral.mapper.ActPrmCateMapper;
import org.springclouddev.integral.service.data_dic.IActPrmCateService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Slf4j
public class ActPrmCateService extends BaseServiceImpl<ActPrmCateMapper, ActPrmCate> implements IActPrmCateService {

    @Override
    public List<ActPrmCate> qryCatesByApCode(String apCode, int pageNum, int pageSize) {
        Query query = new Query();
        query.setSize(pageSize);
        query.setCurrent(pageNum);
        query.setDescs("AP_order");
        return baseMapper.selectPage(Condition.getPage(query), Condition.getQueryWrapper(new ActPrmCate().setApCode(apCode))).getRecords();
    }

    @Override
    public ActPrmCate qryCateByApCodeAndCode(String apCode, String code) {
        return baseMapper.selectOne(Condition.getQueryWrapper(new ActPrmCate().setApCode(apCode).setCode(code)));
    }

    @Override
    public int crtCate(ActPrmCate actPrmCate) {
        return baseMapper.insert(actPrmCate);
    }

    @Override
    public int updCateByApCodeAndCode(ActPrmCate actPrmCate) {
        return baseMapper.updateById(actPrmCate);
    }

    @Override
    public boolean delCateByApCodeAndCode(String apCode, String code) {
        return 1==baseMapper.delete(Condition.getQueryWrapper(new ActPrmCate().setApCode(apCode).setCode(code)));
    }

    @Override
    public List<ActPrmCate> qryStartCatesByApCode(String apCode) {
        return baseMapper.selectList(Condition.getQueryWrapper((ActPrmCate)new ActPrmCate().setApCode(apCode).setStatus(0)));
    }

}
