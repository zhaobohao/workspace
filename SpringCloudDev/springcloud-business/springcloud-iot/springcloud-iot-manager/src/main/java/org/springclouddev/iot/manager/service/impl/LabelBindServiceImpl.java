package org.springclouddev.iot.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springclouddev.core.log.exception.ServiceException;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.manager.dto.LabelBindDto;
import org.springclouddev.iot.manager.entity.LabelBind;
import org.springclouddev.iot.manager.mapper.LabelBindMapper;
import org.springclouddev.iot.manager.service.LabelBindService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>LabelBindService Impl
 */
@Slf4j
@Service
public class LabelBindServiceImpl implements LabelBindService {
    @Resource
    private LabelBindMapper labelBindMapper;

    @Override
    @Caching(
            put = {@CachePut(value = Common.Cache.LABEL_BIND + Common.Cache.ID, key = "#labelBind.id", condition = "#result!=null")},
            evict = {
                    @CacheEvict(value = Common.Cache.LABEL_BIND + Common.Cache.DIC, allEntries = true, condition = "#result!=null"),
                    @CacheEvict(value = Common.Cache.LABEL_BIND + Common.Cache.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public LabelBind add(LabelBind labelBind) {
        if (labelBindMapper.insert(labelBind) > 0) {
            return labelBindMapper.selectById(labelBind.getId());
        }
        throw new ServiceException("The label bind add failed");
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = Common.Cache.LABEL_BIND + Common.Cache.ID, key = "#id", condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.LABEL_BIND + Common.Cache.DIC, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.LABEL_BIND + Common.Cache.LIST, allEntries = true, condition = "#result==true")
            }
    )
    public boolean delete(Long id) {
        LabelBind labelBind = selectById(id);
        if (null == labelBind) {
            throw new ServiceException("The label bind does not exist");
        }
        return labelBindMapper.deleteById(id) > 0;
    }

    @Override
    @Caching(
            put = {@CachePut(value = Common.Cache.LABEL_BIND + Common.Cache.ID, key = "#labelBind.id", condition = "#result!=null")},
            evict = {
                    @CacheEvict(value = Common.Cache.LABEL_BIND + Common.Cache.DIC, allEntries = true, condition = "#result!=null"),
                    @CacheEvict(value = Common.Cache.LABEL_BIND + Common.Cache.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public LabelBind update(LabelBind labelBind) {
        LabelBind temp = selectById(labelBind.getId());
        if (null == temp) {
            throw new ServiceException("The label bind does not exist");
        }
        labelBind.setUpdateTime(null);
        if (labelBindMapper.updateById(labelBind) > 0) {
            return labelBindMapper.selectById(labelBind.getId());
        }
        throw new ServiceException("The label bind update failed");
    }

    @Override
    @Cacheable(value = Common.Cache.LABEL_BIND + Common.Cache.ID, key = "#id", unless = "#result==null")
    public LabelBind selectById(Long id) {
        return labelBindMapper.selectById(id);
    }

    @Override
    @Cacheable(value = Common.Cache.LABEL_BIND + Common.Cache.LIST, keyGenerator = "commonKeyGenerator", unless = "#result==null")
    public Page<LabelBind> list(LabelBindDto labelBindDto) {
        if (!Optional.ofNullable(labelBindDto.getPage()).isPresent()) {
            labelBindDto.setPage(new Pages());
        }
        return labelBindMapper.selectPage(labelBindDto.getPage().convert(), fuzzyQuery(labelBindDto));
    }

    @Override
    public LambdaQueryWrapper<LabelBind> fuzzyQuery(LabelBindDto labelBindDto) {
        LambdaQueryWrapper<LabelBind> queryWrapper = Wrappers.<LabelBind>query().lambda();
        Optional.ofNullable(labelBindDto).ifPresent(dto -> {
            if (null != dto.getLabelId()) {
                queryWrapper.eq(LabelBind::getLabelId, dto.getLabelId());
            }
            if (null != dto.getEntityId()) {
                queryWrapper.eq(LabelBind::getEntityId, dto.getEntityId());
            }
            if (StringUtils.isNotBlank(dto.getType())) {
                queryWrapper.eq(LabelBind::getType, dto.getType());
            }
        });
        return queryWrapper;
    }

}
