package com.mallplus.marking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallplus.common.annotation.SysLog;
import com.mallplus.common.entity.fenxiao.entity.FenxiaoChecks;
import com.mallplus.common.utils.CommonResult;
import com.mallplus.common.utils.ValidatorUtils;
import com.mallplus.common.utils.EasyPoiUtils;
import com.mallplus.marking.service.ISmsFenxiaoChecksService;
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
*  分销审核
*/
@Slf4j
@RestController
@RequestMapping("/fenxiao/fenxiaoChecks")
public class SmsFenxiaoChecksController {

@Resource
private ISmsFenxiaoChecksService IFenxiaoChecksService;

@SysLog(MODULE = "fenxiao", REMARK = "根据条件查询所有分销审核列表")
@ApiOperation("根据条件查询所有分销审核列表")
@GetMapping(value = "/list")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoChecks:read')")
public Object getFenxiaoChecksByPage(FenxiaoChecks entity,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
) {
try {
return new CommonResult().success(IFenxiaoChecksService.page(new Page<FenxiaoChecks>(pageNum, pageSize), new QueryWrapper<>(entity)));
} catch (Exception e) {
log.error("根据条件查询所有分销审核列表：%s", e.getMessage(), e);
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "保存分销审核")
@ApiOperation("保存分销审核")
@PostMapping(value = "/create")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoChecks:create')")
public Object saveFenxiaoChecks(@RequestBody FenxiaoChecks entity) {
try {
    entity.setCreateTime(new Date());
if (IFenxiaoChecksService.save(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("保存分销审核：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "更新分销审核")
@ApiOperation("更新分销审核")
@PostMapping(value = "/update/{id}")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoChecks:update')")
public Object updateFenxiaoChecks(@RequestBody FenxiaoChecks entity) {
try {
if (IFenxiaoChecksService.updateById(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("更新分销审核：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "删除分销审核")
@ApiOperation("删除分销审核")
@GetMapping(value = "/delete/{id}")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoChecks:delete')")
public Object deleteFenxiaoChecks(@ApiParam("分销审核id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("分销审核id");
}
if (IFenxiaoChecksService.removeById(id)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("删除分销审核：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "给分销审核分配分销审核")
@ApiOperation("查询分销审核明细")
@GetMapping(value = "/{id}")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoChecks:read')")
public Object getFenxiaoChecksById(@ApiParam("分销审核id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("分销审核id");
}
FenxiaoChecks coupon = IFenxiaoChecksService.getById(id);
return new CommonResult().success(coupon);
} catch (Exception e) {
log.error("查询分销审核明细：%s", e.getMessage(), e);
return new CommonResult().failed();
}

}

@ApiOperation(value = "批量删除分销审核")
@RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
@SysLog(MODULE = "fenxiao", REMARK = "批量删除分销审核")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoChecks:delete')")
public Object deleteBatch(@RequestParam("ids") List
<Long> ids) {
    boolean count = IFenxiaoChecksService.removeByIds(ids);
    if (count) {
    return new CommonResult().success(count);
    } else {
    return new CommonResult().failed();
    }
    }


    @SysLog(MODULE = "fenxiao", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, FenxiaoChecks entity) {
    // 模拟从数据库获取需要导出的数据
    List<FenxiaoChecks> personList = IFenxiaoChecksService.list(new QueryWrapper<>(entity));
    // 导出操作
    EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", FenxiaoChecks.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "fenxiao", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
    List<FenxiaoChecks> personList = EasyPoiUtils.importExcel(file, FenxiaoChecks.class);
    IFenxiaoChecksService.saveBatch(personList);
    }
    @ApiOperation("修改展示状态")
    @RequestMapping(value = "/update/updateShowStatus")
    @ResponseBody
    @SysLog(MODULE = "cms", REMARK = "修改展示状态")
    public Object updateShowStatus(@RequestParam("ids") Long ids,
                                   @RequestParam("status") Integer status) {
        FenxiaoChecks record = new FenxiaoChecks();
        record.setStatus(status+"");
        record.setId(ids);
        record.setUpdateTime(new Date());

        if (IFenxiaoChecksService.updateById(record)) {
            return new CommonResult().success();
        } else {
            return new CommonResult().failed();
        }
    }
    }


