package com.spring.demo.service.system;

import com.spring.demo.entity.SysLog;
import com.spring.demo.param.SysLogQueryParam;
import com.spring.demo.service.core.IbaseService;
import com.spring.demo.vo.Paging;
import com.spring.demo.vo.SysLogQueryVo;

import java.io.Serializable;

/**
 * <p>
 * 系统日志 服务类
 * </p>
 *
 * @author geekidea
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
