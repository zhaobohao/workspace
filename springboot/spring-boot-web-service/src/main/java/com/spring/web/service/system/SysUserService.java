package com.spring.web.service.system;

import com.spring.web.entity.SysUser;
import com.spring.web.param.SysUserQueryParam;
import com.spring.web.service.core.IbaseService;
import com.spring.web.vo.Paging;
import com.spring.web.vo.SysUserQueryVo;

import java.io.Serializable;

/**
 * <p>
 * SystemUser 服务类
 * </p>
 *
 * @author zhaobohao
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
