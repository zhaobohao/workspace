package org.springclouddev.iot.manager.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.toolkit.Wrappers;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springclouddev.core.log.exception.ServiceException;
import org.springclouddev.iot.common.constant.Common;
import org.springclouddev.iot.manager.dto.DeviceDto;
import org.springclouddev.iot.manager.dto.GroupDto;
import org.springclouddev.iot.manager.entity.Device;
import org.springclouddev.iot.manager.entity.Group;
import org.springclouddev.iot.manager.mapper.GroupMapper;
import org.springclouddev.iot.manager.service.DeviceService;
import org.springclouddev.iot.manager.service.GroupService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.cache.annotation.Caching;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Optional;

/**
 * <p>GroupService Impl
 */
@Slf4j
@Service
public class GroupServiceImpl implements GroupService {
    @Resource
    private DeviceService deviceService;
    @Resource
    private GroupMapper groupMapper;

    @Override
    @Caching(
            put = {
                    @CachePut(value = Common.Cache.GROUP + Common.Cache.ID, key = "#group.id", condition = "#result!=null"),
                    @CachePut(value = Common.Cache.GROUP + Common.Cache.NAME, key = "#group.name", condition = "#result!=null")
            },
            evict = {
                    @CacheEvict(value = Common.Cache.GROUP + Common.Cache.DIC, allEntries = true, condition = "#result!=null"),
                    @CacheEvict(value = Common.Cache.GROUP + Common.Cache.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public Group add(Group group) {
        Group select = selectByName(group.getName());
        Optional.ofNullable(select).ifPresent(g -> {
            throw new ServiceException("The device group already exists");
        });
        if (groupMapper.insert(group) > 0) {
            return groupMapper.selectById(group.getId());
        }
        throw new ServiceException("The group add failed");
    }

    @Override
    @Caching(
            evict = {
                    @CacheEvict(value = Common.Cache.GROUP + Common.Cache.ID, key = "#id", condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.GROUP + Common.Cache.NAME, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.GROUP + Common.Cache.DIC, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.GROUP + Common.Cache.LIST, allEntries = true, condition = "#result==true")
            }
    )
    public boolean delete(Long id) {
        DeviceDto deviceDto = new DeviceDto();
        deviceDto.setGroupId(id);
        Page<Device> devicePage = deviceService.list(deviceDto);
        if (devicePage.getTotal() > 0) {
            throw new ServiceException("The group already bound by the device");
        }
        Group group = selectById(id);
        if (null == group) {
            throw new ServiceException("The group does not exist");
        }
        return groupMapper.deleteById(id) > 0;
    }

    @Override
    @Caching(
            put = {
                    @CachePut(value = Common.Cache.GROUP + Common.Cache.ID, key = "#group.id", condition = "#result!=null"),
                    @CachePut(value = Common.Cache.GROUP + Common.Cache.NAME, key = "#group.name", condition = "#result!=null")
            },
            evict = {
                    @CacheEvict(value = Common.Cache.GROUP + Common.Cache.DIC, allEntries = true, condition = "#result==true"),
                    @CacheEvict(value = Common.Cache.GROUP + Common.Cache.LIST, allEntries = true, condition = "#result!=null")
            }
    )
    public Group update(Group group) {
        Group temp = selectById(group.getId());
        if (null == temp) {
            throw new ServiceException("The group does not exist");
        }
        group.setUpdateTime(null);
        if (groupMapper.updateById(group) > 0) {
            Group select = groupMapper.selectById(group.getId());
            group.setName(select.getName());
            return select;
        }
        throw new ServiceException("The group update failed");
    }

    @Override
    @Cacheable(value = Common.Cache.GROUP + Common.Cache.ID, key = "#id", unless = "#result==null")
    public Group selectById(Long id) {
        return groupMapper.selectById(id);
    }

    @Override
    @Cacheable(value = Common.Cache.GROUP + Common.Cache.NAME, key = "#name", unless = "#result==null")
    public Group selectByName(String name) {
        LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>query().lambda();
        queryWrapper.eq(Group::getName, name);
        return groupMapper.selectOne(queryWrapper);
    }

    @Override
    @Cacheable(value = Common.Cache.GROUP + Common.Cache.LIST, keyGenerator = "commonKeyGenerator", unless = "#result==null")
    public Page<Group> list(GroupDto groupDto) {
        if (!Optional.ofNullable(groupDto.getPage()).isPresent()) {
            groupDto.setPage(new Pages());
        }
        return groupMapper.selectPage(groupDto.getPage().convert(), fuzzyQuery(groupDto));
    }

    @Override
    public LambdaQueryWrapper<Group> fuzzyQuery(GroupDto groupDto) {
        LambdaQueryWrapper<Group> queryWrapper = Wrappers.<Group>query().lambda();
        Optional.ofNullable(groupDto).ifPresent(dto -> {
            if (StringUtils.isNotBlank(dto.getName())) {
                queryWrapper.like(Group::getName, dto.getName());
            }
        });
        return queryWrapper;
    }

}
