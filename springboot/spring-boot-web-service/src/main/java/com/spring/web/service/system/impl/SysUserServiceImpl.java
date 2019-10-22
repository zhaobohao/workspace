package com.spring.web.service.system.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.core.metadata.OrderItem;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.spring.web.dao.entity.SysUser;
import com.spring.web.dao.mapper.SysUserMapper;
import com.spring.web.dao.param.SysUserQueryParam;
import com.spring.web.service.core.impl.BaseServiceImpl;
import com.spring.web.service.system.SysUserService;
import com.spring.web.dao.vo.Paging;
import com.spring.web.dao.vo.SysUserQueryVo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.io.Serializable;


/**
 * <p>
 * SystemUser 服务实现类
 * </p>
 *
 * @author zhaobohao
 * @since 2019-08-26
 */
@Slf4j
@Service
@Transactional(rollbackFor = Exception.class)
public class SysUserServiceImpl extends BaseServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Resource
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
