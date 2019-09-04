package com.spring.demo.service.system.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.demo.entity.SysUser;
import com.spring.demo.mapper.SysUserMapper;
import com.spring.demo.param.SysUserQueryParam;
import com.spring.demo.service.core.impl.BaseServiceImpl;
import com.spring.demo.service.system.SysUserService;
import com.spring.demo.vo.Paging;
import com.spring.demo.vo.SysUserQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;


/**
 * <p>
 * SystemUser 服务实现类
 * </p>
 *
 * @author geekidea
 * @since 2019-08-26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysUserMapper sysUserMapper;

    @Override
    public SysUserQueryVo getSysUserById(Serializable id) throws Exception{
        return sysUserMapper.getSysUserById(id);
    }

    @Override
    public Paging<SysUserQueryVo> getSysUserPageList(SysUserQueryParam sysUserQueryParam) throws Exception{
        Page page = setPageParam(sysUserQueryParam,OrderItem.desc("create_time"));
        IPage<SysUserQueryVo> iPage = sysUserMapper.getSysUserPageList(page,sysUserQueryParam);
        return new Paging(iPage);
    }

}
