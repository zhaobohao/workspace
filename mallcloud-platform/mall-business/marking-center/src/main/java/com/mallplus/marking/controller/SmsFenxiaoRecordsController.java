package com.mallplus.marking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallplus.common.annotation.SysLog;
import com.mallplus.common.entity.fenxiao.entity.FenxiaoRecords;
import com.mallplus.common.utils.CommonResult;
import com.mallplus.common.utils.EasyPoiUtils;
import com.mallplus.common.utils.ValidatorUtils;
import com.mallplus.marking.service.ISmsFenxiaoRecordsService;
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
*  分销记录
*/
@Slf4j
@RestController
@RequestMapping("/fenxiao/fenxiaoRecords")
public class SmsFenxiaoRecordsController {

@Resource
private ISmsFenxiaoRecordsService IFenxiaoRecordsService;

@SysLog(MODULE = "fenxiao", REMARK = "根据条件查询所有分销记录列表")
@ApiOperation("根据条件查询所有分销记录列表")
@GetMapping(value = "/list")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoRecords:read')")
public Object getFenxiaoRecordsByPage(FenxiaoRecords entity,
                                      @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                      @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
) {
try {
return new CommonResult().success(IFenxiaoRecordsService.page(new Page<FenxiaoRecords>(pageNum, pageSize), new QueryWrapper<>(entity)));
} catch (Exception e) {
log.error("根据条件查询所有分销记录列表：%s", e.getMessage(), e);
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "保存分销记录")
@ApiOperation("保存分销记录")
@PostMapping(value = "/create")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoRecords:create')")
public Object saveFenxiaoRecords(@RequestBody FenxiaoRecords entity) {
try {
    entity.setCreateTime(new Date());
if (IFenxiaoRecordsService.save(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("保存分销记录：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "更新分销记录")
@ApiOperation("更新分销记录")
@PostMapping(value = "/update/{id}")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoRecords:update')")
public Object updateFenxiaoRecords(@RequestBody FenxiaoRecords entity) {
try {
if (IFenxiaoRecordsService.updateById(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("更新分销记录：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "删除分销记录")
@ApiOperation("删除分销记录")
@GetMapping(value = "/delete/{id}")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoRecords:delete')")
public Object deleteFenxiaoRecords(@ApiParam("分销记录id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("分销记录id");
}
if (IFenxiaoRecordsService.removeById(id)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("删除分销记录：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "给分销记录分配分销记录")
@ApiOperation("查询分销记录明细")
@GetMapping(value = "/{id}")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoRecords:read')")
public Object getFenxiaoRecordsById(@ApiParam("分销记录id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("分销记录id");
}
FenxiaoRecords coupon = IFenxiaoRecordsService.getById(id);
return new CommonResult().success(coupon);
} catch (Exception e) {
log.error("查询分销记录明细：%s", e.getMessage(), e);
return new CommonResult().failed();
}

}

@ApiOperation(value = "批量删除分销记录")
@RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
@SysLog(MODULE = "fenxiao", REMARK = "批量删除分销记录")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoRecords:delete')")
public Object deleteBatch(@RequestParam("ids") List
<Long> ids) {
    boolean count = IFenxiaoRecordsService.removeByIds(ids);
    if (count) {
    return new CommonResult().success(count);
    } else {
    return new CommonResult().failed();
    }
    }


    @SysLog(MODULE = "fenxiao", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, FenxiaoRecords entity) {
    // 模拟从数据库获取需要导出的数据
    List<FenxiaoRecords> personList = IFenxiaoRecordsService.list(new QueryWrapper<>(entity));
    // 导出操作
    EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", FenxiaoRecords.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "fenxiao", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
    List<FenxiaoRecords> personList = EasyPoiUtils.importExcel(file, FenxiaoRecords.class);
    IFenxiaoRecordsService.saveBatch(personList);
    }
    }


