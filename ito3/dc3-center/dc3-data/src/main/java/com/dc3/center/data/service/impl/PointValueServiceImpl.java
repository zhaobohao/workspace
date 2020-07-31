

package com.dc3.center.data.service.impl;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.center.data.service.PointValueService;
import com.dc3.common.bean.Pages;
import com.dc3.common.bean.driver.PointValue;
import com.dc3.common.bean.driver.PointValueDto;
import com.dc3.common.constant.Common;
import com.dc3.common.exception.ServiceException;
import com.dc3.common.utils.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.CriteriaDefinition;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author pnoker
 */
@Slf4j
@Service
public class PointValueServiceImpl implements PointValueService {

    @Resource
    private RedisUtil redisUtil;
    @Resource
    private MongoTemplate mongoTemplate;

    @Override
    public void add(PointValue pointValue) {
        if (null != pointValue) {
            mongoTemplate.insert(pointValue.setCreateTime(System.currentTimeMillis()));
        }
    }

    @Override
    public void add(List<PointValue> pointValues) {
        pointValues.forEach(pointValue -> pointValue.setCreateTime(System.currentTimeMillis()));
        mongoTemplate.insert(pointValues, PointValue.class);
    }

    @Override
    public Page<PointValue> list(PointValueDto pointValueDto) {
        Criteria criteria = new Criteria();
        if (null == pointValueDto) {
            pointValueDto = new PointValueDto();
        }
        if (null != pointValueDto.getDeviceId()) {
            criteria.and("deviceId").is(pointValueDto.getDeviceId());
        }
        if (null != pointValueDto.getPointId()) {
            criteria.and("pointId").is(pointValueDto.getPointId());
        }
        if (null == pointValueDto.getPage()) {
            pointValueDto.setPage(new Pages());
        }
        Pages pages = pointValueDto.getPage();
        if (pages.getStartTime() > 0 && pages.getEndTime() > 0 && pages.getStartTime() <= pages.getEndTime()) {
            criteria.and("originTime").gte(pages.getStartTime()).lte(pages.getEndTime());
        }
        return queryPage(criteria, pointValueDto.getPage());
    }


    @Override
    public PointValue latest(Long deviceId, Long pointId) {
        Criteria criteria = new Criteria();
        criteria.and("deviceId").is(deviceId);
        criteria.and("pointId").is(pointId);
        return queryOne(criteria);
    }

    @Override
    public String status(Long deviceId) {
        String key = Common.Cache.DEVICE_STATUS_KEY_PREFIX + deviceId;
        String status = redisUtil.getKey(key);
        return null != status ? status : Common.Device.OFFLINE;
    }

    @Override
    public String realtime(Long deviceId, Long pointId) {
        String key = Common.Cache.REAL_TIME_VALUE_KEY_PREFIX + deviceId + "_" + pointId;
        String value = redisUtil.getKey(key);
        if (null == value) {
            throw new ServiceException("No realtime value, Please use '/latest' to get the final data");
        }
        return value;
    }

    /**
     * 查询 One
     *
     * @param criteriaDefinition CriteriaDefinition
     * @return
     */
    private PointValue queryOne(CriteriaDefinition criteriaDefinition) {
        return mongoTemplate.findOne(descQuery(criteriaDefinition), PointValue.class);
    }

    /**
     * 分页&排序&查询
     *
     * @param criteriaDefinition CriteriaDefinition
     * @param pages              Pages
     * @return Page<PointValue>
     */
    private Page<PointValue> queryPage(CriteriaDefinition criteriaDefinition, Pages pages) {
        Query query = descQuery(criteriaDefinition);
        long count = mongoTemplate.count(query, PointValue.class);
        List<PointValue> pointValues = mongoTemplate.find(pageQuery(query, pages), PointValue.class);
        return (new Page<PointValue>()).setCurrent(pages.getCurrent()).setSize(pages.getSize()).setTotal(count).setRecords(pointValues);
    }

    /**
     * 排序
     *
     * @param criteriaDefinition CriteriaDefinition
     * @return Query
     */
    private Query descQuery(CriteriaDefinition criteriaDefinition) {
        Query query = new Query(criteriaDefinition);
        query.with(Sort.by(Sort.Direction.DESC, "originTime"));
        return query;
    }

    /**
     * 分页
     *
     * @param query Query
     * @param pages Pages
     * @return Query
     */
    private Query pageQuery(Query query, Pages pages) {
        int size = (int) pages.getSize();
        long page = pages.getCurrent();
        query.limit(size).skip(size * (page - 1));
        return query;
    }

}
