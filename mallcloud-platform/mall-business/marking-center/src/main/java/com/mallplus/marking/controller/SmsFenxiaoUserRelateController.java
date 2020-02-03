package com.mallplus.marking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallplus.common.annotation.SysLog;
import com.mallplus.common.entity.fenxiao.entity.FenxiaoUserRelate;
import com.mallplus.common.utils.CommonResult;
import com.mallplus.common.utils.ValidatorUtils;
import com.mallplus.marking.service.ISmsFenxiaoUserRelateService;
import com.mallplus.common.utils.EasyPoiUtils;
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
*  分销关系
*/
@Slf4j
@RestController
@RequestMapping("/fenxiao/fenxiaoUserRelate")
public class SmsFenxiaoUserRelateController {

@Resource
private ISmsFenxiaoUserRelateService IFenxiaoUserRelateService;

@SysLog(MODULE = "fenxiao", REMARK = "根据条件查询所有分销关系列表")
@ApiOperation("根据条件查询所有分销关系列表")
@GetMapping(value = "/list")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoUserRelate:read')")
public Object getFenxiaoUserRelateByPage(FenxiaoUserRelate entity,
                                         @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                         @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
) {
try {
return new CommonResult().success(IFenxiaoUserRelateService.page(new Page<FenxiaoUserRelate>(pageNum, pageSize), new QueryWrapper<>(entity)));
} catch (Exception e) {
log.error("根据条件查询所有分销关系列表：%s", e.getMessage(), e);
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "保存分销关系")
@ApiOperation("保存分销关系")
@PostMapping(value = "/create")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoUserRelate:create')")
public Object saveFenxiaoUserRelate(@RequestBody FenxiaoUserRelate entity) {
try {
    entity.setCreateTime(new Date());
if (IFenxiaoUserRelateService.save(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("保存分销关系：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "更新分销关系")
@ApiOperation("更新分销关系")
@PostMapping(value = "/update/{id}")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoUserRelate:update')")
public Object updateFenxiaoUserRelate(@RequestBody FenxiaoUserRelate entity) {
try {
if (IFenxiaoUserRelateService.updateById(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("更新分销关系：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "删除分销关系")
@ApiOperation("删除分销关系")
@GetMapping(value = "/delete/{id}")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoUserRelate:delete')")
public Object deleteFenxiaoUserRelate(@ApiParam("分销关系id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("分销关系id");
}
if (IFenxiaoUserRelateService.removeById(id)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("删除分销关系：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "fenxiao", REMARK = "给分销关系分配分销关系")
@ApiOperation("查询分销关系明细")
@GetMapping(value = "/{id}")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoUserRelate:read')")
public Object getFenxiaoUserRelateById(@ApiParam("分销关系id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("分销关系id");
}
FenxiaoUserRelate coupon = IFenxiaoUserRelateService.getById(id);
return new CommonResult().success(coupon);
} catch (Exception e) {
log.error("查询分销关系明细：%s", e.getMessage(), e);
return new CommonResult().failed();
}

}

@ApiOperation(value = "批量删除分销关系")
@RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
@SysLog(MODULE = "fenxiao", REMARK = "批量删除分销关系")
@PreAuthorize("hasAuthority('fenxiao:fenxiaoUserRelate:delete')")
public Object deleteBatch(@RequestParam("ids") List
<Long> ids) {
    boolean count = IFenxiaoUserRelateService.removeByIds(ids);
    if (count) {
    return new CommonResult().success(count);
    } else {
    return new CommonResult().failed();
    }
    }


    @SysLog(MODULE = "fenxiao", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, FenxiaoUserRelate entity) {
    // 模拟从数据库获取需要导出的数据
    List<FenxiaoUserRelate> personList = IFenxiaoUserRelateService.list(new QueryWrapper<>(entity));
    // 导出操作
    EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", FenxiaoUserRelate.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "fenxiao", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
    List<FenxiaoUserRelate> personList = EasyPoiUtils.importExcel(file, FenxiaoUserRelate.class);
    IFenxiaoUserRelateService.saveBatch(personList);
    }
    }


