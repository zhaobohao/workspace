package com.spring.web.system.web.controller;

import com.spring.web.dao.entity.SysLog;
import com.spring.web.dao.param.IdParam;
import com.spring.web.dao.param.SysLogQueryParam;
import com.spring.web.service.system.SysLogService;
import com.spring.web.dao.vo.Paging;
import com.spring.web.dao.vo.SysLogQueryVo;
import com.spring.web.core.api.ApiResult;
import com.spring.web.core.web.controller.BaseController;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

/**
 * <p>
 * 系统日志 前端控制器
 * </p>
 *
 * @author zhaobohao
 * @since 2019-08-04
 */
@Slf4j
@RestController
@RequestMapping("/sysLog")
@Api("系统日志 API")
public class SysLogController extends BaseController {

    @Autowired
    private SysLogService sysLogService;

    /**
    * 添加系统日志
    */
    @PostMapping("/add")
    @ApiOperation(value = "添加SysLog对象",notes = "添加系统日志",response = ApiResult.class)
    public ApiResult<Boolean> addSysUser(@Valid @RequestBody SysLog sysLog) throws Exception{
        boolean flag = sysLogService.save(sysLog);
        return ApiResult.result(flag);
    }

    /**
    * 修改系统日志
    */
    @PostMapping("/update")
    @ApiOperation(value = "修改SysLog对象",notes = "修改系统日志",response = ApiResult.class)
    public ApiResult<Boolean> updateSysUser(@Valid @RequestBody SysLog sysLog) throws Exception{
        boolean flag = sysLogService.updateById(sysLog);
        return ApiResult.result(flag);
    }

    /**
    * 删除系统日志
    */
    @PostMapping("/delete")
    @ApiOperation(value = "删除SysLog对象",notes = "删除系统日志",response = ApiResult.class)
    public ApiResult<Boolean> deleteSysUser(@Valid @RequestBody IdParam idParam) throws Exception{
        boolean flag = sysLogService.removeById(idParam.getId());
        return ApiResult.result(flag);
    }

    /**
    * 获取系统日志
    */
    @PostMapping("/info")
    @ApiOperation(value = "获取SysLog对象详情",notes = "查看系统日志",response = SysLogQueryVo.class)
    public ApiResult<SysLogQueryVo> getSysUser(@Valid @RequestBody IdParam idParam) throws Exception{
        SysLogQueryVo sysLogQueryVo = sysLogService.getSysLogById(idParam.getId());
        return ApiResult.ok(sysLogQueryVo);
    }

    /**
     * 系统日志分页列表
     */
    @PostMapping("/getPageList")
    @ApiOperation(value = "获取SysLog分页列表",notes = "系统日志分页列表",response = SysLogQueryVo.class)
    public ApiResult<Paging<SysLogQueryVo>> getSysLogPageList(@Valid @RequestBody(required = false) SysLogQueryParam sysLogQueryParam) throws Exception{
        Paging<SysLogQueryVo> paging = sysLogService.getSysLogPageList(sysLogQueryParam);
        return ApiResult.ok(paging);
    }

}

