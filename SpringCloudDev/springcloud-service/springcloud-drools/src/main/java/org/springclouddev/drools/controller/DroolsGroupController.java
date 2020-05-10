package org.springclouddev.drools.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.SystemUser;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.drools.entity.DroolsGroup;
import org.springclouddev.drools.service.IDroolsGroupService;
import org.springclouddev.drools.vo.DroolsGroupVO;
import org.springclouddev.drools.wrapper.DroolsGroupWrapper;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

/**
 * 控制器
 *
 * @author zhaobohao
 * @since 2020-05-11
 */
@RestController
@AllArgsConstructor
@Slf4j
@RequestMapping("/droolsgroup")
@Api(value = "", tags = "接口")
public class DroolsGroupController extends AbstractController {

    private IDroolsGroupService droolsGroupService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入droolsGroup")
    public R<DroolsGroupVO> detail(DroolsGroup droolsGroup) {
        DroolsGroup detail = droolsGroupService.getOne(Condition.getQueryWrapper(droolsGroup));
        return R.data(DroolsGroupWrapper.build().entityVO(detail));
    }

    /**
     * 分页
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入droolsGroup")
    public R<IPage<DroolsGroupVO>> list(DroolsGroup droolsGroup, Query query) {
        IPage<DroolsGroup> pages = droolsGroupService.page(Condition.getPage(query), Condition.getQueryWrapper(droolsGroup));
        return R.data(DroolsGroupWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "分页", notes = "传入droolsGroup")
    public R<IPage<DroolsGroupVO>> page(DroolsGroupVO droolsGroup, Query query) {
        IPage<DroolsGroupVO> pages = droolsGroupService.selectDroolsGroupPage(Condition.getPage(query), droolsGroup);
        return R.data(pages);
    }

    /**
     * 列表
     */
    @GetMapping("/list/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "roleName", value = "参数名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "roleAlias", value = "角色别名", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "父id", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "列表", notes = "传入droolsGroup")
    public R<IPage<DroolsGroup>> list(@ApiIgnore @RequestParam Map<String, Object> droolsGroup, Query query, SystemUser systemUser) {
        QueryWrapper<DroolsGroup> queryWrapper = Condition.getQueryWrapper(droolsGroup, DroolsGroup.class);
        IPage<DroolsGroup> pages = droolsGroupService.page(Condition.getPage(query), queryWrapper);
        return R.data(pages);
    }

    /**
     * 新增
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "新增", notes = "传入droolsGroup")
    public R save(@Valid @RequestBody DroolsGroup droolsGroup) {
        return R.status(droolsGroupService.save(droolsGroup));
    }

    /**
     * 修改
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "修改", notes = "传入droolsGroup")
    public R update(@Valid @RequestBody DroolsGroup droolsGroup) {
        return R.status(droolsGroupService.updateById(droolsGroup));
    }

    /**
     * 新增或修改
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 9)
    @ApiOperation(value = "新增或修改", notes = "传入droolsGroup")
    public R submit(@Valid @RequestBody DroolsGroup droolsGroup) {
        if (droolsGroupService.saveOrUpdate(droolsGroup)) {
            return R.data(droolsGroup);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, droolsGroup, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }


    /**
     * 删除
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 10)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(droolsGroupService.deleteLogic(Func.toLongList(ids)));
    }

    /**
     * 数据源列表
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 12)
    @ApiOperation(value = "下拉数据源", notes = "查询列表")
    public R<List<DroolsGroup>> select() {
        List<DroolsGroup> list = droolsGroupService.list();
        return R.data(list);
    }


}
