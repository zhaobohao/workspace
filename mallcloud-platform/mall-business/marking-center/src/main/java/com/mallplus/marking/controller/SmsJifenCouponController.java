package com.mallplus.marking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallplus.common.annotation.SysLog;
import com.mallplus.common.entity.jifen.entity.JifenCoupon;
import com.mallplus.common.utils.CommonResult;
import com.mallplus.common.utils.ValidatorUtils;
import com.mallplus.marking.service.ISmsJifenCouponService;
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
*  积分券
*/
@Slf4j
@RestController
@RequestMapping("/jifen/jifenCoupon")
public class SmsJifenCouponController {

@Resource
private ISmsJifenCouponService IJifenCouponService;

@SysLog(MODULE = "jifen", REMARK = "根据条件查询所有积分券列表")
@ApiOperation("根据条件查询所有积分券列表")
@GetMapping(value = "/list")
@PreAuthorize("hasAuthority('jifen:jifenCoupon:read')")
public Object getJifenCouponByPage(JifenCoupon entity,
                                   @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                   @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
) {
try {
return new CommonResult().success(IJifenCouponService.page(new Page<JifenCoupon>(pageNum, pageSize), new QueryWrapper<>(entity)));
} catch (Exception e) {
log.error("根据条件查询所有积分券列表：%s", e.getMessage(), e);
}
return new CommonResult().failed();
}

@SysLog(MODULE = "jifen", REMARK = "保存积分券")
@ApiOperation("保存积分券")
@PostMapping(value = "/create")
@PreAuthorize("hasAuthority('jifen:jifenCoupon:create')")
public Object saveJifenCoupon(@RequestBody JifenCoupon entity) {
try {
    entity.setCreateTime(new Date());
if (IJifenCouponService.save(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("保存积分券：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "jifen", REMARK = "更新积分券")
@ApiOperation("更新积分券")
@PostMapping(value = "/update/{id}")
@PreAuthorize("hasAuthority('jifen:jifenCoupon:update')")
public Object updateJifenCoupon(@RequestBody JifenCoupon entity) {
try {
if (IJifenCouponService.updateById(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("更新积分券：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "jifen", REMARK = "删除积分券")
@ApiOperation("删除积分券")
@GetMapping(value = "/delete/{id}")
@PreAuthorize("hasAuthority('jifen:jifenCoupon:delete')")
public Object deleteJifenCoupon(@ApiParam("积分券id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("积分券id");
}
if (IJifenCouponService.removeById(id)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("删除积分券：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "jifen", REMARK = "给积分券分配积分券")
@ApiOperation("查询积分券明细")
@GetMapping(value = "/{id}")
@PreAuthorize("hasAuthority('jifen:jifenCoupon:read')")
public Object getJifenCouponById(@ApiParam("积分券id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("积分券id");
}
JifenCoupon coupon = IJifenCouponService.getById(id);
return new CommonResult().success(coupon);
} catch (Exception e) {
log.error("查询积分券明细：%s", e.getMessage(), e);
return new CommonResult().failed();
}

}

@ApiOperation(value = "批量删除积分券")
@RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
@SysLog(MODULE = "jifen", REMARK = "批量删除积分券")
@PreAuthorize("hasAuthority('jifen:jifenCoupon:delete')")
public Object deleteBatch(@RequestParam("ids") List
<Long> ids) {
    boolean count = IJifenCouponService.removeByIds(ids);
    if (count) {
    return new CommonResult().success(count);
    } else {
    return new CommonResult().failed();
    }
    }


    @SysLog(MODULE = "jifen", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, JifenCoupon entity) {
    // 模拟从数据库获取需要导出的数据
    List<JifenCoupon> personList = IJifenCouponService.list(new QueryWrapper<>(entity));
    // 导出操作
    EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", JifenCoupon.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "jifen", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
    List<JifenCoupon> personList = EasyPoiUtils.importExcel(file, JifenCoupon.class);
    IJifenCouponService.saveBatch(personList);
    }
    }


