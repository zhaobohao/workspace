package org.springclouddev.system.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.github.xiaoymin.knife4j.annotations.ApiOperationSupport;
import io.swagger.annotations.*;
import lombok.AllArgsConstructor;
import org.springclouddev.core.boot.ctrl.AbstractController;
import org.springclouddev.core.mp.support.Condition;
import org.springclouddev.core.mp.support.Query;
import org.springclouddev.core.secure.SystemUser;
import org.springclouddev.core.tool.api.R;
import org.springclouddev.core.tool.constant.ToolConstant;
import org.springclouddev.core.tool.utils.Func;
import org.springclouddev.system.entity.Area;
import org.springclouddev.system.service.IAreaService;
import org.springclouddev.system.vo.AreaVO;
import org.springclouddev.system.wrapper.AreaWrapper;
import org.springframework.web.bind.annotation.*;
import springfox.documentation.annotations.ApiIgnore;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.util.List;
import java.util.Map;

/**
 * 行政区划 控制器
 *
 * @author zhaobohao
 * @since 2019-12-23
 */
@RestController
@AllArgsConstructor
@RequestMapping("/area")
@Api(value = "行政区划", tags = "行政区划接口")
public class AreaController extends AbstractController {

    private IAreaService areaService;

    /**
     * 详情
     */
    @GetMapping("/detail")
    @ApiOperationSupport(order = 1)
    @ApiOperation(value = "详情", notes = "传入area")
    public R<AreaVO> detail(Area area) {
        Area detail = areaService.getOne(Condition.getQueryWrapper(area));
        return R.data(AreaWrapper.build().entityVO(detail));
    }

    /**
     * 分页 行政区划
     */
    @GetMapping("/list")
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "分页", notes = "传入area")
    public R<IPage<AreaVO>> list(Area area, Query query) {
        IPage<Area> pages = areaService.page(Condition.getPage(query), Condition.getQueryWrapper(area));
        return R.data(AreaWrapper.build().pageVO(pages));
    }


    /**
     * 自定义分页 行政区划
     */
    @GetMapping("/page")
    @ApiOperationSupport(order = 3)
    @ApiOperation(value = "分页", notes = "传入area")
    public R<IPage<AreaVO>> page(AreaVO area, Query query) {
        IPage<AreaVO> pages = areaService.selectAreaPage(Condition.getPage(query), area);
        return R.data(pages);
    }

    /**
     * 列表
     */
    @GetMapping("/list/page")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "areaName", value = "区域名称", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "areaCode", value = "区域编码", paramType = "query", dataType = "string"),
            @ApiImplicitParam(name = "parentId", value = "父id", paramType = "query", dataType = "string")
    })
    @ApiOperationSupport(order = 2)
    @ApiOperation(value = "列表", notes = "传入area")
    public R<IPage<Area>> list(@ApiIgnore @RequestParam Map<String, Object> area, Query query, SystemUser systemUser) {
        QueryWrapper<Area> queryWrapper = Condition.getQueryWrapper(area, Area.class);
        IPage<Area> pages = areaService.page(Condition.getPage(query), queryWrapper);
        return R.data(pages);
    }

    /**
     * 新增 行政区划
     */
    @PostMapping("/save")
    @ApiOperationSupport(order = 4)
    @ApiOperation(value = "新增", notes = "传入area")
    public R save(@Valid @RequestBody Area area) {
        return R.status(areaService.save(area));
    }

    /**
     * 修改 行政区划
     */
    @PostMapping("/update")
    @ApiOperationSupport(order = 5)
    @ApiOperation(value = "修改", notes = "传入area")
    public R update(@Valid @RequestBody Area area) {
        return R.status(areaService.updateById(area));
    }

    /**
     * 新增或修改 行政区划
     */
    @PostMapping("/submit")
    @ApiOperationSupport(order = 6)
    @ApiOperation(value = "新增或修改", notes = "传入area")
    public R submit(@Valid @RequestBody Area area) {
        if (areaService.saveOrUpdate(area)) {
            return R.data(area);
        } else {
            return R.data(HttpServletResponse.SC_SERVICE_UNAVAILABLE, area, ToolConstant.DEFAULT_FAILURE_MESSAGE);
        }
    }


    /**
     * 删除 行政区划
     */
    @PostMapping("/remove")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "逻辑删除", notes = "传入ids")
    public R remove(@ApiParam(value = "主键集合", required = true) @RequestParam String ids) {
        return R.status(areaService.deleteLogic(Func.toLongList(ids)));
    }

    /**
     * 数据源列表
     */
    @GetMapping("/select")
    @ApiOperationSupport(order = 8)
    @ApiOperation(value = "下拉数据源", notes = "查询列表")
    public R<List<Area>> select() {
        List<Area> list = areaService.list();
        return R.data(list);
    }
    /**
     * 获取菜单树形结构
     */
    @GetMapping("/tree")
    @ApiOperationSupport(order = 7)
    @ApiOperation(value = "树形结构", notes = "树形结构")
    public R<List<AreaVO>> tree(String parentId) {
        List<AreaVO> tree = areaService.tree(parentId);
        return R.data(tree);
    }
}
