package com.mallplus.marking.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.mallplus.common.annotation.SysLog;
import com.mallplus.common.entity.jifen.entity.JifenSignRule;
import com.mallplus.common.utils.CommonResult;
import com.mallplus.common.utils.ValidatorUtils;
import com.mallplus.common.utils.EasyPoiUtils;
import com.mallplus.marking.service.ISmsJifenSignRuleService;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
* @author mallplus
* @date 2019-12-17
*  积分签到规则
*/
@Slf4j
@RestController
@RequestMapping("/jifen/jifenSignRule")
public class SmsJifenSignRuleController {

@Resource
private ISmsJifenSignRuleService IJifenSignRuleService;

@SysLog(MODULE = "jifen", REMARK = "根据条件查询所有积分签到规则列表")
@ApiOperation("根据条件查询所有积分签到规则列表")
@GetMapping(value = "/list")
@PreAuthorize("hasAuthority('jifen:jifenSignRule:read')")
public Object getJifenSignRuleByPage(JifenSignRule entity,
                                     @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                                     @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize
) {
try {
return new CommonResult().success(IJifenSignRuleService.page(new Page<JifenSignRule>(pageNum, pageSize), new QueryWrapper<>(entity)));
} catch (Exception e) {
log.error("根据条件查询所有积分签到规则列表：%s", e.getMessage(), e);
}
return new CommonResult().failed();
}

@SysLog(MODULE = "jifen", REMARK = "保存积分签到规则")
@ApiOperation("保存积分签到规则")
@PostMapping(value = "/create")
@PreAuthorize("hasAuthority('jifen:jifenSignRule:create')")
public Object saveJifenSignRule(@RequestBody JifenSignRule entity) {
try {

if (IJifenSignRuleService.save(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("保存积分签到规则：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "jifen", REMARK = "更新积分签到规则")
@ApiOperation("更新积分签到规则")
@PostMapping(value = "/update/{id}")
@PreAuthorize("hasAuthority('jifen:jifenSignRule:update')")
public Object updateJifenSignRule(@RequestBody JifenSignRule entity) {
try {
if (IJifenSignRuleService.updateById(entity)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("更新积分签到规则：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "jifen", REMARK = "删除积分签到规则")
@ApiOperation("删除积分签到规则")
@GetMapping(value = "/delete/{id}")
@PreAuthorize("hasAuthority('jifen:jifenSignRule:delete')")
public Object deleteJifenSignRule(@ApiParam("积分签到规则id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("积分签到规则id");
}
if (IJifenSignRuleService.removeById(id)) {
return new CommonResult().success();
}
} catch (Exception e) {
log.error("删除积分签到规则：%s", e.getMessage(), e);
return new CommonResult().failed(e.getMessage());
}
return new CommonResult().failed();
}

@SysLog(MODULE = "jifen", REMARK = "给积分签到规则分配积分签到规则")
@ApiOperation("查询积分签到规则明细")
@GetMapping(value = "/{id}")
@PreAuthorize("hasAuthority('jifen:jifenSignRule:read')")
public Object getJifenSignRuleById(@ApiParam("积分签到规则id") @PathVariable Long id) {
try {
if (ValidatorUtils.empty(id)) {
return new CommonResult().paramFailed("积分签到规则id");
}
JifenSignRule coupon = IJifenSignRuleService.getById(id);
return new CommonResult().success(coupon);
} catch (Exception e) {
log.error("查询积分签到规则明细：%s", e.getMessage(), e);
return new CommonResult().failed();
}

}

@ApiOperation(value = "批量删除积分签到规则")
@RequestMapping(value = "/delete/batch", method = RequestMethod.GET)
@SysLog(MODULE = "jifen", REMARK = "批量删除积分签到规则")
@PreAuthorize("hasAuthority('jifen:jifenSignRule:delete')")
public Object deleteBatch(@RequestParam("ids") List
<Long> ids) {
    boolean count = IJifenSignRuleService.removeByIds(ids);
    if (count) {
    return new CommonResult().success(count);
    } else {
    return new CommonResult().failed();
    }
    }


    @SysLog(MODULE = "jifen", REMARK = "导出社区数据")
    @GetMapping("/exportExcel")
    public void export(HttpServletResponse response, JifenSignRule entity) {
    // 模拟从数据库获取需要导出的数据
    List<JifenSignRule> personList = IJifenSignRuleService.list(new QueryWrapper<>(entity));
    // 导出操作
    EasyPoiUtils.exportExcel(personList, "导出社区数据", "社区数据", JifenSignRule.class, "导出社区数据.xls", response);

    }

    @SysLog(MODULE = "jifen", REMARK = "导入社区数据")
    @PostMapping("/importExcel")
    public void importUsers(@RequestParam MultipartFile file) {
    List<JifenSignRule> personList = EasyPoiUtils.importExcel(file, JifenSignRule.class);
    IJifenSignRuleService.saveBatch(personList);
    }
    }


