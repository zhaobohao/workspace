package com.spring.web.service.system.impl;

import com.spring.web.entity.Ip;
import com.spring.web.mapper.IpMapper;
import com.spring.web.service.core.impl.BaseServiceImpl;
import com.spring.web.service.system.IpService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


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

    @Autowired
    private IpMapper ipMapper;


}
