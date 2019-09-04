package com.spring.demo.service.system;

import com.spring.demo.entity.SysUser;
import com.spring.demo.param.SysUserQueryParam;
import com.spring.demo.service.core.IbaseService;
import com.spring.demo.vo.Paging;
import com.spring.demo.vo.SysUserQueryVo;

import java.io.Serializable;

/**
 * <p>
 * SystemUser 服务类
 * </p>
 *
 * @author geekidea
 * @since 2019-08-26
 */
public interface SysUserService extends IbaseService<SysUser> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    SysUserQueryVo getSysUserById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     * @param sysUserQueryParam
     * @return
     */
    Paging<SysUserQueryVo> getSysUserPageList(SysUserQueryParam sysUserQueryParam) throws Exception;

}
