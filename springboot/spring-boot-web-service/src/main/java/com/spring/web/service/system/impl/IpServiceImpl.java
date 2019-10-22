package com.spring.web.service.system.impl;

import com.spring.web.dao.entity.Ip;
import com.spring.web.dao.mapper.IpMapper;
import com.spring.web.service.core.impl.BaseServiceImpl;
import com.spring.web.service.system.IpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;


/**
 * <p>
 * IP地址 服务实现类
 * </p>
 *
 * @author zhaobohao
 * @since 2019-08-04
 */
@Service
@Transactional(rollbackFor = Exception.class)
@Slf4j
public class IpServiceImpl extends BaseServiceImpl<IpMapper, Ip> implements IpService {

    @Resource
    private IpMapper ipMapper;


}
