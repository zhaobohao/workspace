package com.dc3.transfer.rtmp.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dc3.common.constant.Common;
import com.dc3.common.dto.RtmpDto;
import com.dc3.common.exception.ServiceException;
import com.dc3.common.model.Rtmp;
import com.dc3.transfer.rtmp.bean.Transcode;
import com.dc3.transfer.rtmp.mapper.RtmpMapper;
import com.dc3.transfer.rtmp.service.RtmpService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;


@Slf4j
@Service
public class RtmpServiceImpl implements RtmpService {
    /**
     * 转码任务Map
     */
    public volatile Map<Long, Transcode> transcodeMap = new ConcurrentHashMap<>(16);

    @Resource
    private ThreadPoolExecutor threadPoolExecutor;
    @Resource
    private RtmpMapper rtmpMapper;

    @Override
    @Caching(
            put = {
                    @CachePut(value = Common.Cache.RTMP + Common.Cache.ID, key = "#rtmp.id", condition = "#result!=null")
            },
            evict = {
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.DIC, allEntries = true, condition = "#result!=null"),
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public Rtmp add(Rtmp rtmp) {
        if (rtmpMapper.insert(rtmp) > 0) {
            Transcode transcode = new Transcode(rtmp);
            if (!transcodeMap.containsKey(transcode.getId())) {
                transcodeMap.put(transcode.getId(), transcode);
            }
            return selectById(rtmp.getId());
        }
        throw new ServiceException("The rtmp task add failed");
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.ID, key = "#id", condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.DIC, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.LIST, allEntries = true, condition = "#result==true")
            }
    )
    public boolean delete(Long id) {
        Rtmp select = selectById(id);
        if (null != select) {
            Transcode transcode = transcodeMap.get(id);
            if (Optional.ofNullable(transcode).isPresent()) {
                if (transcode.isRun()) {
                    throw new ServiceException("The rmp task is running");
                }
                transcodeMap.remove(id);
            }
            return rtmpMapper.deleteById(id) > 0;
        }
        throw new ServiceException("The rtmp task does not exist");
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = Common.Cache.RTMP + Common.Cache.ID, key = "#rtmp.id", condition = "#result!=null")
            },
            evict = {
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.DIC, allEntries = true, condition = "#result!=null"),
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public Rtmp update(Rtmp rtmp) {
        rtmp.setUpdateTime(null);
        Rtmp select = selectById(rtmp.getId());
        if (null == select) {
            throw new ServiceException("The rtmp task does not exist");
        }
        Transcode transcode = transcodeMap.get(rtmp.getId());
        if (Optional.ofNullable(transcode).isPresent()) {
            if (transcode.isRun()) {
                throw new ServiceException("The rtmp task is running");
            }
            transcodeMap.put(transcode.getId(), new Transcode(rtmp));
        }
        if (rtmpMapper.updateById(rtmp) > 0) {
            return rtmp;
        }
        throw new ServiceException("The rtmp task update failed");
    }

    @Override
    @Cacheable(value = Common.Cache.RTMP + Common.Cache.ID, key = "#id", unless = "#result==null")
    public Rtmp selectById(Long id) {
        return rtmpMapper.selectById(id);
    }

    @Override
    @Cacheable(value = Common.Cache.RTMP + Common.Cache.LIST, keyGenerator = "commonKeyGenerator", unless = "#result==null")
    public Page<Rtmp> list(RtmpDto rtmpDto) {
        return rtmpMapper.selectPage(rtmpDto.getPage().convert(), fuzzyQuery(rtmpDto));
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.ID, key = "#id", condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.DIC, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.LIST, allEntries = true, condition = "#result==true")
            }
    )
    public boolean start(Long id) {
        Rtmp select = rtmpMapper.selectById(id);
        if (null == select) {
            throw new ServiceException("The rtmp task does not exist");
        }
        Transcode transcode = transcodeMap.get(id);
        if (Optional.ofNullable(transcode).isPresent()) {
            if (transcode.isRun()) {
                throw new ServiceException("The rtmp task is running");
            }
        } else {
            transcode = new Transcode(select);
            transcodeMap.put(transcode.getId(), transcode);
        }
        threadPoolExecutor.execute(() -> transcodeMap.get(id).start());
        if (rtmpMapper.updateById(select.setRun(true)) > 0) {
            return true;
        }
        throw new ServiceException("The rtmp task started successfully，database update failed");
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.ID, key = "#id", condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.DIC, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.RTMP + Common.Cache.LIST, allEntries = true, condition = "#result==true")
            }
    )
    public boolean stop(Long id) {
        Rtmp select = rtmpMapper.selectById(id);
        if (null != select) {
            Transcode transcode = transcodeMap.get(id);
            if (Optional.ofNullable(transcode).isPresent()) {
                if (transcode.isRun()) {
                    transcode.stop();
                    if (rtmpMapper.updateById(select.setRun(false)) > 0) {
                        return true;
                    }
                    throw new ServiceException("The rtmp task stop failed，database update failed");
                }
            }
            throw new ServiceException("The rtmp task is stopped");
        }
        throw new ServiceException("The rtmp task does not exist");
    }

    @Override
    public LambdaQueryWrapper<Rtmp> fuzzyQuery(RtmpDto rtmpDto) {
        LambdaQueryWrapper<Rtmp> queryWrapper = Wrappers.<Rtmp>query().lambda();
        Optional.ofNullable(rtmpDto).ifPresent(dto -> {
            if (StringUtils.isNotBlank(dto.getName())) {
                queryWrapper.like(Rtmp::getName, dto.getName());
            }
            Optional.ofNullable(dto.getAutoStart()).ifPresent(autoStart -> queryWrapper.eq(Rtmp::getAutoStart, dto.getAutoStart()));
        });
        return queryWrapper;
    }

}
