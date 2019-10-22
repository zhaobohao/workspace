package com.spring.web.service.system;

import com.spring.web.entity.SysLog;
import com.spring.web.param.SysLogQueryParam;
import com.spring.web.service.core.IbaseService;
import com.spring.web.vo.Paging;
import com.spring.web.vo.SysLogQueryVo;

import java.io.Serializable;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author zhaobohao
 * @since 2019-08-04
 */
public interface SysLogService extends IbaseService<SysLog> {

    /**
     * 根据ID获取查询对象
     * @param id
     * @return
     */
    SysLogQueryVo getSysLogById(Serializable id) throws Exception;

    /**
     * 获取分页对象
     * @param sysLogQueryParam
     * @return
     */
    Paging<SysLogQueryVo> getSysLogPageList(SysLogQueryParam sysLogQueryParam) throws Exception;

}
