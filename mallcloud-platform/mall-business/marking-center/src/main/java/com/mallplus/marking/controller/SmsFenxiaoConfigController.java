package com.mallplus.marking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallplus.common.annotation.SysLog;
import com.mallplus.common.entity.fenxiao.entity.FenxiaoConfig;
import com.mallplus.common.utils.CommonResult;
import com.mallplus.common.utils.ValidatorUtils;
import com.mallplus.common.utils.EasyPoiUtils;
import com.mallplus.marking.service.ISmsFenxiaoConfigService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;
import java.util.List;

/**
 * @author mallplus
 * @date 2019-12-17
 * 分销配置
 */
@Slf4j
@RestController
@RequestMapping("/fenxiao/fenxiaoConfig")
public class SmsFenxiaoConfigController {

    @Resource
    private ISmsFenxiaoConfigService IFenxiaoConfigService;

    @SysLog(MODULE = "fenxiao", REMARK = "根据条件查询所有分销配置列表")
    @ApiOperation("根据条件查询所有分销配置列表")
    @GetMapping(value = "/list")
    @PreAuthorize("hasAuthority('fenxiao:fenxiaoConfig:read')")
    public Object getFenxiaoConfigByPage(FenxiaoConfig entity,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
    ) {
        try {
            return new CommonResult().success(IFenxiaoConfigService.page(new Page<FenxiaoConfig>(pageNum, pageSize), new QueryWrapper<>(entity)));
        } catch (Exception e) {
            log.error("根据条件查询所有分销配置列表：%s", e.getMessage(), e);
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "fenxiao", REMARK = "保存分销配置")
    @ApiOperation("保存分销配置")
    @PostMapping(value = "/create")
    @PreAuthorize("hasAuthority('fenxiao:fenxiaoConfig:create')")
    public Object saveFenxiaoConfig(@RequestBody FenxiaoConfig entity) {
        try {
            entity.setCreateTime(new Date());
            if (IFenxiaoConfigService.save(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("保存分销配置：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "fenxiao", REMARK = "更新分销配置")
    @ApiOperation("更新分销配置")
    @PostMapping(value = "/update/{id}")
    @PreAuthorize("hasAuthority('fenxiao:fenxiaoConfig:update')")
    public Object updateFenxiaoConfig(@RequestBody FenxiaoConfig entity) {
        try {
            if (IFenxiaoConfigService.updateById(entity)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("更新分销配置：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "fenxiao", REMARK = "删除分销配置")
    @ApiOperation("删除分销配置")
    @GetMapping(value = "/delete/{id}")
    @PreAuthorize("hasAuthority('fenxiao:fenxiaoConfig:delete')")
    public Object deleteFenxiaoConfig(@ApiParam("分销配置id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("分销配置id");
            }
            if (IFenxiaoConfigService.removeById(id)) {
                return new CommonResult().success();
            }
        } catch (Exception e) {
            log.error("删除分销配置：%s", e.getMessage(), e);
            return new CommonResult().failed(e.getMessage());
        }
        return new CommonResult().failed();
    }

    @SysLog(MODULE = "fenxiao", REMARK = "给分销配置分配分销配置")
    @ApiOperation("查询分销配置明细")
    @GetMapping(value = "/{id}")
    @PreAuthorize("hasAuthority('fenxiao:fenxiaoConfig:read')")
    public Object getFenxiaoConfigById(@ApiParam("分销配置id") @PathVariable Long id) {
        try {
            if (ValidatorUtils.empty(id)) {
                return new CommonResult().paramFailed("分销配置id");
            }
            FenxiaoConfig coupon = IFenxiaoConfigService.getOne(new QueryWrapper<>());
            return new CommonResult().success(coupon);
        } catch (Exception e) {
            log.error("查询分销配置明细：%s", e.getMessage(), e);
            return new CommonResult().failed();
        }

    }

    @ApiOperation(value = "批量删除分销配置")
    @RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
    @SysLog(MODULE = "fenxiao", REMARK = "批量删除分销配置")
    @PreAuthorize("hasAuthority('fenxiao:fenxiaoConfig:delete')")
    public Object deleteBatch(@RequestParam("ids") List
            <Long> ids) {
        boolean count = IFenxiaoConfigService.removeByIds(ids);
        if (count) {
            return new CommonResult().success(count);
        } else {
            return new CommonResult().failed();
        }
    }


    @SysLog(MODULE = "fenxiao", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, FenxiaoConfig entity) {
        // 模拟从数据库获取需要导出的数据
        List<FenxiaoConfig> personList = IFenxiaoConfigService.list(new QueryWrapper<>(entity));
        // 导出操作
        EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", FenxiaoConfig.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "fenxiao", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
        List<FenxiaoConfig> personList = EasyPoiUtils.importExcel(file, FenxiaoConfig.class);
        IFenxiaoConfigService.saveBatch(personList);
    }
}


