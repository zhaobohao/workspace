package org.springclouddev.integral.service.impl.actIntegral;

import cn.hutool.core.collection.CollectionUtil;
import cn.hutool.core.util.StrUtil;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springclouddev.core.mp.base.BaseServiceImpl;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.integral.entity.IntegralAct;
import org.springclouddev.integral.entity.LatestActCode;
import org.springclouddev.integral.entity.MarketingAct;
import org.springclouddev.integral.mapper.IntegralActMapper;
import org.springclouddev.integral.mapper.LatestActCodeMapper;
import org.springclouddev.integral.service.IMarketingActService;
import org.springclouddev.integral.service.actIntegral.IintegralActService;
import org.springclouddev.integral.service.latestActCode.ILatestActCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;


@Service
@Slf4j
public class IntegralActService extends BaseServiceImpl<IntegralActMapper, IntegralAct> implements IintegralActService {

    @Autowired
    private LatestActCodeMapper latestActCodeMapper;

    @Autowired
    private ILatestActCodeService latestActCodeService;

    @Autowired
    private IMarketingActService marketingActService;

    private static Logger logger = LoggerFactory.getLogger(IntegralActService.class);


    /**
     * 查询所有的积分活动
     */
    @Override
    public List<IntegralAct> queryAllAct() {

        IntegralAct example = new IntegralAct();
        example.setStatus(0);
        List<IntegralAct> list = baseMapper.selectList(Condition.getQueryWrapper(example).orderByDesc("update_time"));
        if (CollectionUtil.isEmpty(list)) {
            return null;
        }
        List<IntegralAct> list1 = new ArrayList<IntegralAct>();
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        for (IntegralAct IntegralAct : list) {
            try {
                Date endTime = sdf.parse(IntegralAct.getEndTime());
                String time1 = sdf.format(new Date());
                Date curTime = sdf.parse(time1);
                if (endTime.compareTo(curTime) > 0) {
                    list1.add(IntegralAct);
                }
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }
        return list1;

    }


    /**
     * 根据积分活动id查询
     */
    @Override
    public List<IntegralAct> queryByActCode(String actCode) {

        if (StringUtils.isEmpty(actCode)) {
            queryAllAct();
        }

        IntegralAct example = new IntegralAct();
        example.setActCode(actCode);
        List<IntegralAct> list = null;
        try {
            list = baseMapper.selectList(Condition.getQueryWrapper(example));
        } catch (Exception e) {
            logger.error("数据库查询异常，" + e.getMessage());
        }
        return list;

    }


    /**
     * 根据积分活动name查询
     */
    @Override
    public List<IntegralAct> queryByActName(String name) {

        if (StringUtils.isEmpty(name)) {
            queryAllAct();
        }
        Map<String, String> map = new HashMap<>();
        map.put("actName_like", name);
        IntegralAct example = new IntegralAct();
        List<IntegralAct> list = null;
        try {
            list = baseMapper.selectList(Condition.getQueryWrapper(example).orderByDesc("update_time desc"));
        } catch (Exception e) {
            logger.error("数据库查询异常，" + e.getMessage());
        }
        return list;

    }

    /**
     * 分页查询
     */

    public List<IntegralAct> findPage(int page, int limit, String actCode, String actName) {
        Query query = new Query();
        query.setCurrent(page);
        query.setSize(limit);
        query.setDescs("update_time");
        Map<String, Object> map = new HashMap<>();
        if (StrUtil.isNotBlank(actCode)) {
            map.put("actCode", actCode);
        }
        if (StrUtil.isNotBlank(actName)) {
            map.put("actName_like", actName);
        }
        List<IntegralAct> page1 = null;
        try {
            page1 = baseMapper.selectPage(Condition.getPage(query), Condition.getQueryWrapper(map, IntegralAct.class)).getRecords();
        } catch (Exception e) {
            logger.error("数据库查询异常," + e.getMessage());
        }

        return page1;
    }

    @Override
    /**
     * 插入积分活动
     */
    @Transactional
    public String insertIntegralAct(IntegralAct integralAct) {

        if (StringUtils.isEmpty(integralAct.getMarketActId()) || StringUtils.isEmpty(integralAct.getActName())) {
            return "缺少必输项";
        }

        MarketingAct marketingAct = new MarketingAct();
        marketingAct.setActCode(integralAct.getMarketActId());
        List<MarketingAct> list1 = marketingActService.getMarketingActList(marketingAct);
        integralAct.setEndTime(list1.get(0).getEndTime());
        int retultCode = baseMapper.insert(integralAct);
        // 修改tbl_latest_act_code表中的最新活动编号
        LatestActCode latestActCode = new LatestActCode();
        String suffixActCode1 = latestActCodeService.queryActCode().get(0).getSuffixActCode();
        int length = suffixActCode1.length();
        String suffixActCode2 = String.valueOf(Integer.parseInt(suffixActCode1) + 1);
        StringBuffer suffixActCode3 = new StringBuffer();
        if (suffixActCode2.length() < length) {
            int a = length - suffixActCode2.length();
            for (int i = 0; i < a; i++) {
                suffixActCode3.append("0");
            }
        }
        String suffixActCode = suffixActCode3.append(suffixActCode2).toString();
        latestActCode.setId(1L);
        LatestActCode latestActCode1 = new LatestActCode();
        latestActCode1.setSuffixActCode(suffixActCode);
        latestActCodeMapper.update(latestActCode1, Condition.getQueryWrapper(latestActCode));
        return "添加成功";
    }


    /**
     * 修改积分活动
     */
    @Override
    public String updateActivity(IntegralAct integralAct) {

        if (integralAct == null) {
            return "要修改的对象为空";
        }
        int status = integralAct.getStatus();
        // 只有 01提交审核，和02审核通过 的活动不能修改 ，其他状态都能修改
        if  (0==status || 4==status) {
            return "该状态的活动不能修改";
        }
        String actCode = integralAct.getActCode();
        int resultCode = -1;
        try {
            resultCode = baseMapper.updateById(integralAct);
        } catch (Exception e) {
            logger.error("数据局修改数据失败" + e.getMessage());
        }

        return "修改成功";

    }

    /**
     * 通过actCode删除积分活动
     */
    @Override
    public String deleteByActCode(String actCode) {

        if (StringUtils.isEmpty(actCode)) {
            return "缺少必输项";
        }
        List<IntegralAct> list = queryByActCode(actCode);
        if (list == null && list.size() == 0) {
            return "积分活动code在数据库不存在";
        }
        int status = list.get(0).getStatus();
        if ((!StringUtils.isEmpty(status))
                && (3==status || 0==status)) {
            return "对不起，该状态的活动不允许删除";
        }
        try {
            int resultCode = baseMapper.delete(Condition.getQueryWrapper(new IntegralAct().setActCode(actCode)));
        } catch (Exception e) {
            logger.error("删除数据失败，" + e.getMessage());
        }

        return "删除成功";
    }


    /**
     * 提交审核
     */
    @Override
    public String pushToAudit(String actCode) {

        if (StringUtils.isEmpty(actCode)) {
            return "缺少必输项actCode";
        }

        List<IntegralAct> list = baseMapper.selectList(Condition.getQueryWrapper(new IntegralAct().setActCode(actCode)));
        if (list == null && list.size() == 0) {
            return "acId不存在";
        }
        IntegralAct integralAct =  list.get(0);
        int status = integralAct.getStatus();
        if (0==status || 3==status) {
            return "该状态的活动不允许提交二次提交审核";
        }
        integralAct.setStatus(4);
        try {
            int resultMsg = baseMapper.updateById(integralAct);
        } catch (Exception e) {
            logger.error("删除数据失败" + e.getMessage());
        }
        return "修改成功";
    }

    @Override
    /**
     *审核通过
     */
    public String auditPass(IntegralAct integralAct) {

        if (integralAct == null) {
            return "缺少必输项";
        }
        List<IntegralAct> list1 = null;
        if (!StringUtils.isEmpty(integralAct.getActCode())) {
            list1 = queryByActCode(integralAct.getActCode());
        }
        if (4!=list1.get(0).getStatus()) {
            return "对不起，不能审核该状态的活动";
        }
        try {
            integralAct.setStatus(0);
            int resultMsg = baseMapper.updateById(integralAct);
        } catch (Exception e) {
            logger.error("修改数据失败" + e.getMessage());
        }

        return "修改成功";
    }


    @Override
    /**
     *  退回
     */
    public String getBackActintegral(IntegralAct integralAct) {

        if (StringUtils.isEmpty(integralAct == null)) {
            return "缺少必输项";
        }
        List<IntegralAct> list1 = null;
        if (!StringUtils.isEmpty(integralAct.getActCode())) {
            list1 = queryByActCode(integralAct.getActCode());
        }
        if (4!=list1.get(0).getStatus()) {
            return "对不起，不能审核该状态的活动";
        }
        integralAct.setStatus(1);

        try {
            int resultMsg = baseMapper.updateById(integralAct);
        } catch (Exception e) {
            logger.error("修改数据失败，" + e.getMessage());
        }
        return "修改成功";
    }


    @Override
    /**
     * 审核页面--只显示待审核和审核通过的数据
     */
    public List<IntegralAct> queryAboutAduit(int page, int limit, String actCode, String actName) {
        IntegralAct example = new IntegralAct();
        Query query=new Query();
        query.setCurrent(page);
        query.setSize(limit);
        query.setDescs("update_time");
        Map<String,Object> map=new HashMap<>();

        if (StrUtil.isNotBlank(actCode)) {
            map.put("actCode",actCode);
        }
        if (StrUtil.isNotBlank(actName)) {
            map.put("actName_like",actName);
        }
        List<IntegralAct> list = null;
        try {
            list = baseMapper.selectPage(Condition.getPage(query),Condition.getQueryWrapper(map,IntegralAct.class).in("status",0,4)).getRecords();
        } catch (Exception e) {
            logger.error("查询失败" + e.getMessage());
            return list;
        }
        return list;
    }


    @Override
    public long getStatusCount(String status) {
        return baseMapper.selectCount(Condition.getQueryWrapper(new IntegralAct().setState(status)));
    }


}
